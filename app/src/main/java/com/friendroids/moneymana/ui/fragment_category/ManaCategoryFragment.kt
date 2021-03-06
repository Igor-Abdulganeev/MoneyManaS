package com.friendroids.moneymana.ui.fragment_category

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.friendroids.moneymana.R
import com.friendroids.moneymana.data.repository.ManaFragmentCategoryRepository
import com.friendroids.moneymana.db.DataBase
import com.friendroids.moneymana.db.models.CategoryEntity
import com.friendroids.moneymana.db.models.CheckEntity
import com.friendroids.moneymana.ui.fragment_category.adapter.ActionChecks
import com.friendroids.moneymana.ui.fragment_category.adapter.ManaCategoryAdapter
import com.friendroids.moneymana.ui.fragment_category.viewmodel.FragmentCategoryViewModel
import com.friendroids.moneymana.ui.fragment_category.viewmodel.FragmentCategoryViewModelFactory

class ManaCategoryFragment : Fragment(R.layout.fragment_mana_category_info) {

    private val fragmentCategoryViewModel: FragmentCategoryViewModel by viewModels {
        FragmentCategoryViewModelFactory(
            ManaFragmentCategoryRepository(DataBase.getInstance(requireContext().applicationContext))
        )
    }

    private lateinit var nameCategory: TextView
    private lateinit var imageCategory: ImageView
    private lateinit var progressBarCategory: ProgressBar
    private lateinit var list: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.initViews()
        initObserves()
        loadData()
    }

    private fun View.initViews() {
        nameCategory = findViewById(R.id.item_category_name_text_view)
        imageCategory = findViewById(R.id.item_category_image_view)
        progressBarCategory = findViewById(R.id.item_category_custom_progress_bar)
        list = findViewById(R.id.mana_category_recycler_view)
    }

    private fun initObserves() {
        fragmentCategoryViewModel.manaChecks.observe(this.viewLifecycleOwner, this::updateChecks)
        fragmentCategoryViewModel.category.observe(this.viewLifecycleOwner, this::updateCategory)
    }

    private fun loadData() {
        val categoryId = requireNotNull(arguments?.getLong(CATEGORY_ID_KEY))
        fragmentCategoryViewModel.updateManaProgress(categoryId)
    }

    private fun updateChecks(checksEntity: List<CheckEntity>) {
        val adapter = ManaCategoryAdapter(checksEntity, clickListener)
        list.adapter = adapter
        list.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    private fun updateCategory(categoryEntity: CategoryEntity) {
        nameCategory.text = categoryEntity.title
        imageCategory.setImageResource(categoryEntity.imageId)
        progressBarCategory.progress =
            100 - ((categoryEntity.sumRemained.toFloat() / categoryEntity.sumMaximum.toFloat()) * 100).toInt()
    }

    private val clickListener = object : ActionChecks {
        override fun onRemove(idCheck: Long?) {
            fragmentCategoryViewModel.removeCheck(idCheck!!)
        }
    }

    companion object {
        private const val CATEGORY_ID_KEY = "id_category"
        fun newInstance(idCategory: Long) = ManaCategoryFragment().apply {
            arguments = Bundle().apply {
                putLong(CATEGORY_ID_KEY, idCategory)
            }
        }
    }
}