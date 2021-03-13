package com.friendroids.moneymana.ui.fragment_category.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.friendroids.moneymana.domain.repository.ManaRepository

class FragmentCategoryViewModelFactory(private val manaRepository: ManaRepository) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        FragmentCategoryViewModel::class.java -> FragmentCategoryViewModel(manaRepository)
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T

}