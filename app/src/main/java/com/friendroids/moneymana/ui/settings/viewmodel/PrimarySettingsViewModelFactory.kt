package com.friendroids.moneymana.ui.settings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.friendroids.moneymana.domain.repository.SettingsRepository

class PrimarySettingsViewModelFactory(private val settingsRepository: SettingsRepository) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        PrimarySettingsViewModel::class.java -> PrimarySettingsViewModel(settingsRepository)
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}