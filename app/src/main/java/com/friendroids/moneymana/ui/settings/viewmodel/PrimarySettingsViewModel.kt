package com.friendroids.moneymana.ui.settings.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.friendroids.moneymana.db.models.TotalBudgetEntity
import com.friendroids.moneymana.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PrimarySettingsViewModel(private val settingsRepository: SettingsRepository) : ViewModel() {

    private val _settings = MutableLiveData<TotalBudgetEntity>()
    val settings: LiveData<TotalBudgetEntity> get() = _settings

    init {
        viewModelScope.launch {
            settingsRepository.getPrimaryBudgetSettings().collect {
                _settings.value = it
            }
        }
    }

    fun updateSettings(settings: TotalBudgetEntity) {
        viewModelScope.launch {
            settingsRepository.updatePrimaryBudgetSettings(settings)
        }
    }
}
