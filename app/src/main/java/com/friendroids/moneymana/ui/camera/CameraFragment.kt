package com.friendroids.moneymana.ui.camera

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.friendroids.moneymana.R
import com.friendroids.moneymana.data.camera.ManaCameraX
import com.friendroids.moneymana.databinding.FragmentCameraBinding
import java.util.concurrent.Executor

class CameraFragment : Fragment() {

    private var _binding: FragmentCameraBinding? = null
    private val binding get() = _binding!!

    private lateinit var cameraX: ManaCameraX
    private lateinit var executor: Executor

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentCameraBinding.inflate(inflater, container, false)
        .run {
            _binding = this
            binding.root
        }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        executor = ContextCompat.getMainExecutor(requireContext())
        cameraX = ManaCameraX(requireContext(), executor, binding.cameraView, viewLifecycleOwner)
        cameraX.qrCode.observe(viewLifecycleOwner, Observer { qrc ->
            qrc?.let {
                val list = it.split("&")
                val date = "${list[0].subSequence(8, 10)}.${
                    list[0].subSequence(
                        6,
                        8
                    )
                }.${list[0].subSequence(2, 6)}"
                val sum = list[1].substring(2)
                binding.dateTextedit.setText(date)
                binding.moneyTextedit.setText(sum)
            }
        })
        cameraX.bindCamera()

        bindSpinner()
    }

    private fun bindSpinner() {
        val listCategory = listOf<String>("Еда", "Не еда", "Совсем не еда", "По умолчанию")
        val listAdapter = ArrayAdapter(requireContext(), R.layout.item_category, listCategory)
        listAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.categorySpinner.adapter = listAdapter
        binding.categorySpinner.setSelection(3)
    }

    fun scan() {
        cameraX.scanQRCode()
    }

    companion object {
        fun newInstance() = CameraFragment()
    }

}