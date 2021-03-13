package com.friendroids.moneymana.ui.fragment_category

import android.content.Context
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
import com.friendroids.moneymana.data.repository.ManaRepositoryImpl
import com.friendroids.moneymana.db.DataBase
import com.friendroids.moneymana.db.models.CheckEntity
import com.friendroids.moneymana.ui.fragment_category.adapter.ManaCategoryAdapter
import com.friendroids.moneymana.ui.fragment_category.viewmodel.FragmentCategoryViewModel
import com.friendroids.moneymana.ui.fragment_category.viewmodel.FragmentCategoryViewModelFactory
import com.friendroids.moneymana.ui.presentation_models.ManaCategory
import java.util.*

class ManaCategoryFragment : Fragment(R.layout.fragment_mana_category_info) {

    private var listener: ActionCategory? = null
    private val fragmentCategoryViewModel: FragmentCategoryViewModel by viewModels {
        FragmentCategoryViewModelFactory(
            ManaRepositoryImpl(DataBase.getInstance(requireContext().applicationContext))
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

//        findViewById<ImageView>(R.id.path).setOnClickListener {
//            listener?.exitFragment()
//        }
//        findViewById<TextView>(R.id.caption_back_text_view).setOnClickListener {
//            listener?.exitFragment()
//        }

    }

    private fun initObserves() {
        fragmentCategoryViewModel.manaCategories.observe(this.viewLifecycleOwner, this::updCategory)
    }


    private fun loadData() {
        val categoryId = requireNotNull(arguments?.getInt(CATEGORY_ID_KEY))
        fragmentCategoryViewModel.updateManaProgress(categoryId)
    }

    private fun updCategory(manaCategory: ManaCategory) {

        nameCategory.text = manaCategory.title
        imageCategory.setImageResource(manaCategory.imageId)
        progressBarCategory.progress = manaCategory.percentRemained


        val checks = listOf(
            CheckEntity(1, 1, Date(10101), 123456.0),
            CheckEntity(1, 1, Date(11032021000001), 120.0),
            CheckEntity(1, 1, Date(12032021000001), 233.0),
            CheckEntity(1, 1, Date(13032021000011), 5.0),
            CheckEntity(1, 1, Date(13032021000201), 634.0),
            CheckEntity(1, 1, Date(13032021010001), 100000.0),
            CheckEntity(1, 1, Date(13032021000011), 5.0),
            CheckEntity(1, 1, Date(13032021000201), 634.0),
            CheckEntity(1, 1, Date(13032021010001), 100000.0)
        )


        val adapter = ManaCategoryAdapter(checks)
        list.adapter = adapter
        list.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ActionCategory) listener = context

    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    companion object {
        private const val CATEGORY_ID_KEY = "id_category"
        fun newInstance(idCategory: Int) = ManaCategoryFragment().apply {
            arguments = Bundle().apply {
                putInt(CATEGORY_ID_KEY, idCategory)
            }
        }
    }

    interface ActionCategory {
        fun exitFragment()
        fun openDatePickerFragment(nameMovie: String)
    }
}