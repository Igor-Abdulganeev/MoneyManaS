package com.friendroids.moneymana.ui.fragment_category

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.friendroids.moneymana.R
import com.friendroids.moneymana.data.repository.ManaCategoriesRepositoryImpl
import com.friendroids.moneymana.databinding.FragmentManaCategoryInfoBinding
import com.friendroids.moneymana.db.ManaDatabase
import com.friendroids.moneymana.db.models.CheckEntity
import com.friendroids.moneymana.ui.fragment_category.adapter.ManaCategoryAdapter
import com.friendroids.moneymana.ui.fragment_category.viewmodel.ManaCategoryViewModel
import com.friendroids.moneymana.ui.fragment_category.viewmodel.ManaCategoryViewModelFactory

class ManaCategoryFragment : Fragment() {

    private var _binding: FragmentManaCategoryInfoBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ManaCategoryAdapter

    //    private var listener: ActionCategory? = null
    private val manaCategoryViewModel: ManaCategoryViewModel by viewModels {
        ManaCategoryViewModelFactory(
            ManaCategoriesRepositoryImpl(ManaDatabase.getInstance(requireContext().applicationContext))
        )
    }

/*
    private lateinit var nameCategory: TextView
    private lateinit var imageCategory: ImageView
    private lateinit var progressBarCategory: ProgressBar
    private lateinit var list: RecyclerView
*/

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentManaCategoryInfoBinding.inflate(inflater, container, false)
        .run {
            _binding = this
            binding.root
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObserve()
        loadData()
    }

    private fun initObserve() {
        manaCategoryViewModel.manaChecks.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initViews() {
        adapter = ManaCategoryAdapter()
        with(binding) {
            val imageCategory = requireNotNull(arguments?.getInt(CATEGORY_IMAGE_KEY))
            checksRecyclerView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            checksRecyclerView.adapter = adapter
            itemCategoryNameTextView.text =
                arguments?.getString(CATEGORY_NAME_KEY) ?: getString(R.string.error_name_category)
            itemCategoryImageView.setImageResource(imageCategory)
        }
    }

    private fun loadData() {
        val categoryId = requireNotNull(arguments?.getLong(CATEGORY_ID_KEY))
        manaCategoryViewModel.loadChecks(categoryId)
    }

    /*
        private fun updateCategory(checksEntity: List<CheckEntity>) {

            nameCategory.text = ""// manaCategory.title
            imageCategory.setImageResource(R.drawable.beaker_check)
            progressBarCategory.progress = 33

            val adapter = ManaCategoryAdapter()
            list.adapter = adapter
            list.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        }

        override fun onAttach(context: Context) {
            super.onAttach(context)
    //        if (context is ActionCategory) listener = context

        }

        override fun onDetach() {
            super.onDetach()
     //       listener = null
        }
        */
    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    companion object {
        private const val CATEGORY_ID_KEY = "id_category"
        private const val CATEGORY_IMAGE_KEY = "image_category"
        private const val CATEGORY_NAME_KEY = "name_category"
        fun newInstance(idCategory: Long, imageCategory: Int, nameCategory: String) =
            ManaCategoryFragment().apply {
                arguments = Bundle().apply {
                    putLong(CATEGORY_ID_KEY, idCategory)
                    putInt(CATEGORY_IMAGE_KEY, imageCategory)
                    putString(CATEGORY_NAME_KEY, nameCategory)
                }
            }
    }

/*
    interface ActionCategory {
        fun exitFragment()
        fun openDatePickerFragment(nameMovie: String)
    }
*/
}