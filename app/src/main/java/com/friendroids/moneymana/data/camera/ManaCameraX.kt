package com.friendroids.moneymana.data.camera

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.mlkit.vision.barcode.Barcode
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.Executor

class ManaCameraX(
    private val context: Context,
    private val executor: Executor,
    private val previewView: PreviewView,
    private val lifecycle: LifecycleOwner
) {

    private var cameraLens = CameraSelector.LENS_FACING_BACK
    private lateinit var imageCapture: ImageCapture
    private lateinit var inputImage: InputImage

    private var _qrCode = MutableLiveData<String>()
    val qrCode: LiveData<String>
        get() = _qrCode

    fun bindCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        cameraProviderFuture.addListener(
            {
                val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
                bindPreview(cameraProvider)
            },
            executor
        )
    }

    private fun bindPreview(cameraProvider: ProcessCameraProvider) {
        val preview = Preview.Builder()
            .setTargetAspectRatio(AspectRatio.RATIO_16_9)
            .setTargetRotation(previewView.display.rotation)
            .build()
            .also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }
        val cameraSelector = CameraSelector.Builder()
            .requireLensFacing(cameraLens)
            .build()
        imageCapture = ImageCapture.Builder()
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY)
            .setTargetRotation(previewView.display.rotation)
            .build()
        val camera = cameraProvider.bindToLifecycle(
            lifecycle,
            cameraSelector,
            imageCapture,
            preview
        )
    }

    private fun scanImage() {
        val options = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(
                Barcode.FORMAT_QR_CODE
            )
            .build()
        val scanner = BarcodeScanning.getClient(options)
        if (::inputImage.isInitialized) {
            val result = scanner.process(inputImage)
                .addOnSuccessListener { barcodes ->
                    for (barcode in barcodes) {
                        when (barcode.valueType) {
                            Barcode.TYPE_TEXT -> {
                                Log.d("ManaCameraX", "ТЕКСТ СКАНА = ${barcode.displayValue}")
                                _qrCode.value = barcode.displayValue
                            }
                        }

                    }
                }
                .addOnFailureListener {
                    Log.d("ManaCameraX", " ОШИБКА СКАНА")
                }
        }
    }

    fun scanQRCode() {
        imageCapture.takePicture(
            executor,
            object : ImageCapture.OnImageCapturedCallback() {
                @SuppressLint("UnsafeExperimentalUsageError")
                override fun onCaptureSuccess(imageProxy: ImageProxy) {
                    // super.onCaptureSuccess(image)
                    Log.d("ManaCameraX", "Фото сделано удачно")
                    val newImage = imageProxy.image
                    if (newImage != null) {
                        inputImage = InputImage.fromMediaImage(
                            newImage,
                            imageProxy.imageInfo.rotationDegrees
                        )
                        scanImage()
                        imageProxy.close()
                    }
                }

                override fun onError(exception: ImageCaptureException) {
                    Log.d("ManaCameraX", "Ошибка съемки = ${exception.message}")
                    Toast.makeText(
                        context,
                        "Ошибка съемки = ${exception.message}",
                        Toast.LENGTH_LONG
                    ).show()
                    super.onError(exception)
                }
            }
        )
    }
}