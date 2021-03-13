package com.friendroids.moneymana.ui.new_category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.friendroids.moneymana.data.repository.ManaRepositoryImpl
import com.friendroids.moneymana.databinding.DialogAddCategoryBinding
import com.friendroids.moneymana.db.DataBase
import com.friendroids.moneymana.ui.new_category.viewmodel.AddManaCategoryViewModel
import com.friendroids.moneymana.ui.new_category.viewmodel.AddManaCategoryViewModelFactory
import com.friendroids.moneymana.ui.presentation_models.ManaCategory

class AddCategoryDialog : DialogFragment() {

    private var _binding: DialogAddCategoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AddManaCategoryViewModel by viewModels {
        AddManaCategoryViewModelFactory(
            ManaRepositoryImpl(DataBase.getInstance(requireContext().applicationContext))
        )
    }

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
        initView()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initView() {
        binding.saveCategoryButton.setOnClickListener {
            val newCategory = setupNewCategory()
            newCategory?.let { updateDataBase(newCategory) }
            dismiss()
        }
    }

    private fun setupNewCategory(): ManaCategory? {
        val title = binding.categoryTitleEditText.text.toString()
        val limit = binding.sumLimitEditText.text.toString()
        return if (title.isNotBlank() && limit.isNotBlank()) {
            val newCategory = ManaCategory(
                title = title,
                sumRemained = limit.toInt(),
                maxSum = limit.toInt()
            )
            newCategory
        } else null
    }

    private fun updateDataBase(newCategory: ManaCategory) {
        viewModel.insertToDataBase(newCategory)
    }

    companion object {
        const val ADD_CATEGORY_DIALOG_TAG = "ADD_CATEGORY_DIALOG_TAG"
    }
}
