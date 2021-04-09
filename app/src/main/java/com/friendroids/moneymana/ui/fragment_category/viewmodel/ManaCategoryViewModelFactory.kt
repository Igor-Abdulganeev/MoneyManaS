package com.friendroids.moneymana.ui.fragment_category.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.friendroids.moneymana.domain.repository.ManaCategoriesRepository

class ManaCategoryViewModelFactory(
    private val manaCategoriesRepository: ManaCategoriesRepository
) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        ManaCategoryViewModel::class.java -> ManaCategoryViewModel(manaCategoriesRepository)
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T

}