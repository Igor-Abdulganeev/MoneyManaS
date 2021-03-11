package com.friendroids.moneymana.ui.main_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.friendroids.moneymana.data.repository.ManaRepositoryImpl
import com.friendroids.moneymana.databinding.FragmentMainBinding
import com.friendroids.moneymana.ui.main_screen.adapter.ManaCategoriesAdapter
import com.friendroids.moneymana.ui.main_screen.viewmodel.ManaViewModel
import com.friendroids.moneymana.ui.main_screen.viewmodel.ManaViewModelFactory

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ManaViewModel by viewModels {
        ManaViewModelFactory(
            ManaRepositoryImpl()
        )
    }
    private lateinit var manaCategoriesAdapter: ManaCategoriesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        viewModel.getUserManaState()
        viewModel.manaCategories.observe(viewLifecycleOwner, { manaCategoriesAdapter.bindItems(it) })
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initRecycler() {
        manaCategoriesAdapter = ManaCategoriesAdapter {
            viewModel.updateManaProgress(it)
        }
        binding.manaRecyclerView.adapter = manaCategoriesAdapter
    }
}
