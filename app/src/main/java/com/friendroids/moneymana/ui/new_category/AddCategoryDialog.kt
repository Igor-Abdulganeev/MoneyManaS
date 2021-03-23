package com.friendroids.moneymana.ui.new_category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.friendroids.moneymana.R
import com.friendroids.moneymana.data.repository.ManaRepositoryImpl
import com.friendroids.moneymana.databinding.DialogAddCategoryBinding
import com.friendroids.moneymana.db.DataBase
import com.friendroids.moneymana.ui.new_category.viewmodel.AddManaCategoryViewModel
import com.friendroids.moneymana.ui.new_category.viewmodel.AddManaCategoryViewModelFactory
import com.friendroids.moneymana.ui.presentation_models.ManaCategory
import com.friendroids.moneymana.utils.extensions.changeColor

class AddCategoryDialog : DialogFragment() {

    private var _binding: DialogAddCategoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AddManaCategoryViewModel by viewModels {
        AddManaCategoryViewModelFactory(
            ManaRepositoryImpl(DataBase.getInstance(requireContext().applicationContext))
        )
    }

    private lateinit var adapter: AddCategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogAddCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setLayout(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
        initViews()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initViews() {
        with(binding) {
            categoryTitleEditText.addTextChangedListener { validateButton() }
            sumLimitEditText.addTextChangedListener { validateButton() }
            saveCategoryButton.setOnClickListener {
                updateDataBase(setupNewCategory())
                dismiss()
            }
        }

        val listImage: List<Int> = listOf(
            R.drawable.adjust,
            R.drawable.ambulance,
            R.drawable.bag_suitcase,
            R.drawable.baseball_bat,
            R.drawable.beaker_check,
            R.drawable.drama_masks,
            R.drawable.face_man_profile,
            R.drawable.food,
            R.drawable.food_fork_drink,
            R.drawable.food_variant,
            R.drawable.fuel,
            R.drawable.gas_station,
            R.drawable.hiking,
            R.drawable.trash_can_outline
        )

        adapter = AddCategoryAdapter(listImage)
        binding.recyclerViewId.adapter = adapter
        binding.recyclerViewId.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    private fun validateButton() {
        with(binding) {
            saveCategoryButton.isEnabled =
                categoryTitleEditText.text?.isNotBlank() == true && sumLimitEditText.text?.isNotBlank() == true
            if (saveCategoryButton.isEnabled) saveCategoryButton.changeColor(
                    requireContext(),
                    R.color.primaryColor
            )
            else saveCategoryButton.changeColor(requireContext(), R.color.light_grey)
        }
    }

    private fun setupNewCategory() =
        ManaCategory(
                title = binding.categoryTitleEditText.text.toString(),
                sumRemained = binding.sumLimitEditText.text.toString().toInt(),
                sumMaximum = binding.sumLimitEditText.text.toString().toInt(),
                imageId = adapter.idSave
        )

    private fun updateDataBase(newCategory: ManaCategory) {
        viewModel.insertToDataBase(newCategory)
    }

    companion object {
        const val ADD_CATEGORY_DIALOG_TAG = "ADD_CATEGORY_DIALOG_TAG"
    }
}
