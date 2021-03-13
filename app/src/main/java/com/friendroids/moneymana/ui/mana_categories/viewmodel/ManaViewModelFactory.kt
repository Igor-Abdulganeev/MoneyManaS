package com.friendroids.moneymana.ui.mana_categories.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.friendroids.moneymana.domain.repository.ManaRepository

class ManaViewModelFactory(private val manaRepository: ManaRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        ManaViewModel::class.java -> ManaViewModel(manaRepository)
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}
