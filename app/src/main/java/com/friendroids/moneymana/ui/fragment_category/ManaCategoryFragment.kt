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
import com.friendroids.moneymana.data.repository.ManaFragmentCategoryRepository
import com.friendroids.moneymana.db.ManaDatabase
import com.friendroids.moneymana.db.models.CheckEntity
import com.friendroids.moneymana.ui.fragment_category.adapter.ManaCategoryAdapter
import com.friendroids.moneymana.ui.fragment_category.viewmodel.FragmentCategoryViewModel
import com.friendroids.moneymana.ui.fragment_category.viewmodel.FragmentCategoryViewModelFactory
import java.util.*

class ManaCategoryFragment : Fragment(R.layout.fragment_mana_category_info) {

    private var listener: ActionCategory? = null
    private val fragmentCategoryViewModel: FragmentCategoryViewModel by viewModels {
        FragmentCategoryViewModelFactory(
            ManaFragmentCategoryRepository(ManaDatabase.getInstance(requireContext().applicationContext))
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
        fragmentCategoryViewModel.manaChecks.observe(this.viewLifecycleOwner, this::updCategory)
    }

    private fun loadData() {
        val categoryId = requireNotNull(arguments?.getInt(CATEGORY_ID_KEY))
        fragmentCategoryViewModel.updateManaProgress(categoryId)
    }

    private fun updCategory(checksEntity: List<CheckEntity>) {

        nameCategory.text = ""// manaCategory.title
        imageCategory.setImageResource(R.drawable.beaker_check)
        progressBarCategory.progress = 33

        val adapter = ManaCategoryAdapter(checksEntity)
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
        fun newInstance(idCategory: Long) = ManaCategoryFragment().apply {
            arguments = Bundle().apply {
                putLong(CATEGORY_ID_KEY, idCategory)
            }
        }
    }

    interface ActionCategory {
        fun exitFragment()
        fun openDatePickerFragment(nameMovie: String)
    }
}