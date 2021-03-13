package com.friendroids.moneymana.ui.mana_categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.friendroids.moneymana.data.repository.ManaRepositoryImpl
import com.friendroids.moneymana.databinding.FragmentManaCategoriesBinding
import com.friendroids.moneymana.db.DataBase
import com.friendroids.moneymana.ui.mana_categories.adapter.ManaCategoriesAdapter
import com.friendroids.moneymana.ui.mana_categories.viewmodel.ManaCategoriesViewModel
import com.friendroids.moneymana.ui.mana_categories.viewmodel.ManaViewModelFactory
import com.friendroids.moneymana.ui.new_category.AddCategoryDialog
import com.friendroids.moneymana.ui.new_category.AddCategoryDialog.Companion.ADD_CATEGORY_DIALOG_TAG
import com.friendroids.moneymana.ui.settings.PrimarySettingsDialog
import com.friendroids.moneymana.ui.settings.PrimarySettingsDialog.Companion.PRIMARY_SETTINGS_DIALOG

class ManaCategoriesFragment : Fragment() {

    private var _binding: FragmentManaCategoriesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ManaCategoriesViewModel by viewModels {
        ManaViewModelFactory(
            ManaRepositoryImpl(DataBase.getInstance(requireContext().applicationContext))
        )
    }
    private lateinit var manaCategoriesAdapter: ManaCategoriesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentManaCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        viewModel.getUserManaState()
        viewModel.manaCategories.observe(
            viewLifecycleOwner,
            { manaCategoriesAdapter.bindItems(it) })
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initViews() {
        manaCategoriesAdapter = ManaCategoriesAdapter {
            viewModel.updateManaProgress(it)
        }
        binding.manaRecyclerView.adapter = manaCategoriesAdapter
        with(binding) {
            addCategoryButton.setOnClickListener {
                val dialog = AddCategoryDialog()
                dialog.show(childFragmentManager, ADD_CATEGORY_DIALOG_TAG)
            }
            primarySettingsButton.setOnClickListener {
                val dialog = PrimarySettingsDialog()
                dialog.show(childFragmentManager, PRIMARY_SETTINGS_DIALOG)
            }
        }
        getDataFromSharedPrefs()
    }

    private fun getDataFromSharedPrefs() {
        //todo
    }
}
