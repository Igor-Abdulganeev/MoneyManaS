package com.friendroids.moneymana.ui.camera

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.friendroids.moneymana.R
import com.friendroids.moneymana.data.camera.ManaCameraX
import com.friendroids.moneymana.data.repository.ManaCategoriesRepositoryImpl
import com.friendroids.moneymana.databinding.FragmentCameraBinding
import com.friendroids.moneymana.db.ManaDatabase
import com.friendroids.moneymana.domain.repository.ManaCategoriesRepository
import com.friendroids.moneymana.ui.NavigationActivity
import com.friendroids.moneymana.ui.presentation_models.Category
import com.friendroids.moneymana.ui.presentation_models.Check
import com.friendroids.moneymana.ui.presentation_models.ManaCategory
import com.friendroids.moneymana.utils.extensions.DateTimeConverter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.concurrent.Executor

class CameraFragment : Fragment() {

    private var navigationActivity: NavigationActivity? = null
    private var _binding: FragmentCameraBinding? = null
    private val binding get() = _binding!!

    private lateinit var cameraX: ManaCameraX
    private lateinit var executor: Executor
    private lateinit var categoriesRepository: ManaCategoriesRepository
    private lateinit var scanningCheck: Check

    private var _listSpinner = MutableLiveData<List<ManaCategory>>()
    private val listSpinner: LiveData<List<ManaCategory>>
        get() = _listSpinner
    private lateinit var listAdapter: CameraSpinAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        navigationActivity = context as? NavigationActivity
    }

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
        categoriesRepository =
            ManaCategoriesRepositoryImpl(ManaDatabase.getInstance(requireContext().applicationContext))
        binding.dateTextedit.setText(DateTimeConverter().setCurrentDateTimeToString())
        executor = ContextCompat.getMainExecutor(requireContext())
        bindCamera()
        bindSpinner()
        getCategoryList()
        binding.notButton.setOnClickListener { activity?.onBackPressed() }

        binding.addButton.setOnClickListener {
            val sum = binding.moneyTextedit.text.toString()
            try {
                if (sum.toDouble() != 0.00) {
//                    repoOut = PurchaseDB(requireContext())
                    appendCheck()
                }
            } catch (e: Exception) {
                Log.e(TAG, "Ошибка добавления чека в БД = $e")
            }

        }
    }

    private fun appendCheck() {
        viewLifecycleOwner.lifecycleScope
                .launch {
                    val scanDate = binding.dateTextedit.text.toString()
                    Log.d(TAG, "дата1 = $scanDate")
                    val scanSum = binding.moneyTextedit.text.toString().toDouble()
                    Log.d(TAG, "sum2 = $scanSum")
                    val selectedSpin = binding.categorySpinner.selectedItem as ManaCategory
                    val dateToDB = DateTimeConverter().setDateTimeStringToLong(scanDate)
                    val category =
                        Category(selectedSpin.id, selectedSpin.imageId, selectedSpin.title)
                    var fnCheck = 0L
                    var iCheck = 0L
                    var fpCheck = 0L
                    if (::scanningCheck.isInitialized) {
                        fnCheck = scanningCheck.fnCheck
                        iCheck = scanningCheck.iCheck
                        fpCheck = scanningCheck.fpCheck
                    }
                    val check = Check(
                        id = null,
                        dateCheck = dateToDB,
                        category = category,
                        sumCheck = scanSum,
                        fnCheck = fnCheck,
                        iCheck = iCheck,
                        fpCheck = fpCheck
                    )
//                    Log.d(TAG, "$dateToDB=${category.id}=${category.imageId}=${category.title}=$scanSum")
                    categoriesRepository.insertCheck(check = check)
                    activity?.onBackPressed()
                }
    }

    override fun onResume() {
        super.onResume()
        navigationActivity?.setImageResource(R.drawable.add_shopping)
    }

    private fun bindCamera() {
        cameraX = ManaCameraX(requireContext(), executor, binding.cameraView, viewLifecycleOwner)
        cameraX.qrCode.observe(viewLifecycleOwner, { qrc ->
            qrc?.let {
                //ТЕКСТ СКАНА = t=20210315T180100&s=234.60&fn=9960440300119563&i=7611&fp=3036044891&n=1
                val list = it.split("&")
                val dateCheck = "${list[0].subSequence(8, 10)}" +
                        ".${list[0].subSequence(6, 8)}" +
                        ".${list[0].subSequence(2, 6)}" +
                        " ${list[0].subSequence(11, 13)}" +
                        ":${list[0].subSequence(13, 15)}"
                val sum = list[1].substring(2)
                val fn = list[2].substring(3).toLong()
                val i = list[3].substring(2).toLong()
                val fp = list[4].substring(3).toLong()
                scanningCheck = Check(
                        id = 0,
                        dateCheck = 0,
                        category = Category(0, 0, ""),
                        sumCheck = 0.00,
                        fnCheck = fn,
                        iCheck = i,
                        fpCheck = fp
                )
                binding.dateTextedit.setText(dateCheck)
                binding.moneyTextedit.setText(sum)
            }
        })
        cameraX.bindCamera()
    }

    private fun bindSpinner() {
        listSpinner.observe(viewLifecycleOwner, {
            listAdapter = CameraSpinAdapter(requireContext(), it)
            binding.categorySpinner.adapter = listAdapter
        })
    }

    fun scan() {
        cameraX.scanQRCode()
    }

    companion object {
        fun newInstance() = CameraFragment()
        private const val TAG = "CameraFragment"
    }

    private fun getCategoryList() {
        viewLifecycleOwner.lifecycleScope.launch {
            categoriesRepository.getListCategory().collect {
                _listSpinner.value = it
            }
        }
    }

}