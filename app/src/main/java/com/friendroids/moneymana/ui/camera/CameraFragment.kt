package com.friendroids.moneymana.ui.camera

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.friendroids.moneymana.R
import com.friendroids.moneymana.data.camera.ManaCameraX
import com.friendroids.moneymana.data.repository.ManaRepositoryImpl
import com.friendroids.moneymana.databinding.FragmentCameraBinding
import com.friendroids.moneymana.db.DataBase
import com.friendroids.moneymana.db.PurchaseDB
import com.friendroids.moneymana.domain.repository.ManaRepository
import com.friendroids.moneymana.ui.NavigationActivity
import com.friendroids.moneymana.ui.presentation_models.Categorie
import com.friendroids.moneymana.ui.presentation_models.Check
import com.friendroids.moneymana.ui.presentation_models.ManaCategory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import java.util.concurrent.Executor

class CameraFragment : Fragment() {

    private var navigationActivity: NavigationActivity? = null
    private var _binding: FragmentCameraBinding? = null
    private val binding get() = _binding!!

    private lateinit var cameraX: ManaCameraX
    private lateinit var executor: Executor
    private lateinit var repoIn: ManaRepository
    private lateinit var repoOut: PurchaseDB

    private var _listSpinner = MutableLiveData<List<ManaCategory>>()
    private val listSpinner: LiveData<List<ManaCategory>>
        get() = _listSpinner
    private lateinit var listAdapter: SpinAdapter

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

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repoIn = ManaRepositoryImpl(DataBase.getInstance(requireContext().applicationContext))
        val currentDate = Date()
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val date = dateFormat.format(currentDate)
        binding.dateTextedit.setText(date)
        executor = ContextCompat.getMainExecutor(requireContext())
        bindCamera()
        bindSpinner()
        getCategoryList()
        binding.notButton.setOnClickListener { activity?.onBackPressed() }

        binding.addButton.setOnClickListener {
            val sum = binding.moneyTextedit.text.toString()
            try {
                if (sum.toDouble() != 0.00) {
                    viewLifecycleOwner.lifecycleScope.launch {
                        repoOut = PurchaseDB(requireContext())
                        val date = binding.dateTextedit.text.toString()
                        val sum = binding.moneyTextedit.text.toString().toDouble()
                        val spin = listSpinner.value
                        val selectedSpin = binding.categorySpinner.selectedItem as ManaCategory

                        val year = date.subSequence(6, 10).toString().toInt()
                        val month = date.subSequence(3, 5).toString().toInt()
                        val day = date.subSequence(0, 2).toString().toInt()
                        val curDate = Date(year, month, day)
                        val cat =
                            Categorie(selectedSpin.id!!, selectedSpin.imageId, selectedSpin.title)
                        val check: Check = Check(
                            id = 0,
                            dateCheck = curDate,
                            categorie = cat,
                            summa = sum
                        )

                        Log.d(TAG, "$curDate=${cat.id}=${cat.imageId}=${cat.title}=$sum")
                        repoOut.updateCheckInDB(check, Date())
                        activity?.onBackPressed()
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Ошибка добавления чека в БД = $e")
            }

        }
    }

    override fun onResume() {
        super.onResume()
        navigationActivity?.setImageResource(R.drawable.add_shopping_24)
    }

    private fun bindCamera() {
        cameraX = ManaCameraX(requireContext(), executor, binding.cameraView, viewLifecycleOwner)
        cameraX.qrCode.observe(viewLifecycleOwner, Observer { qrc ->
            qrc?.let {
                val list = it.split("&")
                val dateCheck = "${list[0].subSequence(8, 10)}.${
                    list[0].subSequence(
                        6,
                        8
                    )
                }.${list[0].subSequence(2, 6)}"
                val sum = list[1].substring(2)
                binding.dateTextedit.setText(dateCheck)
                binding.moneyTextedit.setText(sum)
            }
        })
        cameraX.bindCamera()
    }

    private fun bindSpinner() {
        listSpinner.observe(viewLifecycleOwner, {
            listAdapter = SpinAdapter(requireContext(), it)
            binding.categorySpinner.adapter = listAdapter
        })
/*
        val listCategory = listOf(ManaCategory(1, "Прочее", 1, 1, R.drawable.add_shopping_24))
        listAdapter = SpinAdapter(requireContext(), listCategory)
        listAdapter.setDropDownViewResource(R.layout.item_category)
        binding.categorySpinner.adapter = listAdapter
*/
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
            _listSpinner.value = repoIn.getListCategory()
        }
    }

    private class SpinAdapter(
        private val contextMain: Context,
        private val arrayCategory: List<ManaCategory>
    ) :
        ArrayAdapter<ManaCategory>(contextMain, R.layout.item_category, arrayCategory) {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            return getItemsView(position, convertView, parent)
        }

        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
            return getItemsView(position, convertView, parent)
        }

        private fun getItemsView(position: Int, convertView: View?, parent: ViewGroup): View {
            val inflater = LayoutInflater.from(contextMain)
            val row: View = inflater.inflate(R.layout.item_category, parent, false)
            val nameCategory = row.findViewById<TextView>(R.id.category_name_text)
            val imageCategory = row.findViewById<ImageView>(R.id.category_image)

            imageCategory.setImageResource(arrayCategory[position].imageId)
            nameCategory.text = arrayCategory[position].title

            return row
        }
    }
}