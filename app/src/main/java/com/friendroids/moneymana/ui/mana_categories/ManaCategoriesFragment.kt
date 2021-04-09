package com.friendroids.moneymana.ui.mana_categories

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.friendroids.moneymana.R
import com.friendroids.moneymana.data.repository.ManaCategoriesRepositoryImpl
import com.friendroids.moneymana.data.repository.SettingsRepositoryImpl
import com.friendroids.moneymana.databinding.FragmentManaCategoriesBinding
import com.friendroids.moneymana.db.ManaDatabase
import com.friendroids.moneymana.db.models.TotalBudgetEntity
import com.friendroids.moneymana.ui.NavigationActivity
import com.friendroids.moneymana.ui.mana_categories.adapter.ManaCategoriesAdapter
import com.friendroids.moneymana.ui.mana_categories.viewmodel.ManaCategoriesViewModel
import com.friendroids.moneymana.ui.mana_categories.viewmodel.ManaViewModelFactory
import com.friendroids.moneymana.ui.settings.PrimarySettingsDialog
import com.friendroids.moneymana.ui.settings.PrimarySettingsDialog.Companion.PRIMARY_SETTINGS_DIALOG

class ManaCategoriesFragment : Fragment() {
    private var navigationActivity: NavigationActivity? = null
    private var _binding: FragmentManaCategoriesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ManaCategoriesViewModel by viewModels {
        val db = ManaDatabase.getInstance(requireContext().applicationContext)
        ManaViewModelFactory(
            ManaCategoriesRepositoryImpl(db = db),
            SettingsRepositoryImpl(db = db)
        )
    }
    private lateinit var manaCategoriesAdapter: ManaCategoriesAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        navigationActivity = context as? NavigationActivity
    }

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
        initObservers()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getUserManaState()
        navigationActivity?.setImageResource(R.drawable.ic_scanner)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun initObservers() {
        viewModel.manaCategories.observe(
            viewLifecycleOwner,
            { manaCategoriesAdapter.bindItems(it) })
        viewModel.primarySettings.observe(viewLifecycleOwner, ::setupPrimarySettingsInfo)
    }

    private fun initViews() {
        manaCategoriesAdapter = ManaCategoriesAdapter {
            navigationActivity?.openCategoryFragment(it.id, it.imageId, it.title)
        }
        with(binding) {
            manaRecyclerView.adapter = manaCategoriesAdapter
/*
            addCategoryButton.setOnClickListener {
                val dialog = AddCategoryDialog()
                dialog.show(childFragmentManager, ADD_CATEGORY_DIALOG_TAG)
            }
*/
            primarySettingsButton.setOnClickListener {
                val dialog = PrimarySettingsDialog()
                dialog.show(childFragmentManager, PRIMARY_SETTINGS_DIALOG)
            }
        }
    }

    private fun setupPrimarySettingsInfo(settings: TotalBudgetEntity) {
        binding.primarySettingsTextView.text =
            getString(R.string.budget_settings, settings.sumBudget, settings.dayRestart)
    }
}
