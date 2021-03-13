package com.friendroids.moneymana.ui.new_category.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.friendroids.moneymana.domain.repository.ManaRepository

class AddManaCategoryViewModelFactory(private val manaRepository: ManaRepository) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        AddManaCategoryViewModel::class.java -> AddManaCategoryViewModel(manaRepository)
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}