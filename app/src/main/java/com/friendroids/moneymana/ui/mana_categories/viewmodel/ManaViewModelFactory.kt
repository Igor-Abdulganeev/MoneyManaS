package com.friendroids.moneymana.ui.mana_categories.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.friendroids.moneymana.domain.repository.ManaCategoriesRepository
import com.friendroids.moneymana.domain.repository.SettingsRepository

class ManaViewModelFactory(
    private val manaCategoriesRepository: ManaCategoriesRepository,
    private val settingsRepository: SettingsRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        ManaCategoriesViewModel::class.java -> ManaCategoriesViewModel(
            manaCategoriesRepository,
            settingsRepository
        )
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}
