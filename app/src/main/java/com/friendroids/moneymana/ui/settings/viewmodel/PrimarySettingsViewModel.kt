package com.friendroids.moneymana.ui.settings.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.friendroids.moneymana.db.models.TotalBudgetEntity
import com.friendroids.moneymana.domain.repository.SettingsRepository
import com.friendroids.moneymana.utils.extensions.DateTimeConverter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

class PrimarySettingsViewModel(private val settingsRepository: SettingsRepository) : ViewModel() {

    private val _settings = MutableLiveData<TotalBudgetEntity>()
    val settings: LiveData<TotalBudgetEntity> get() = _settings

    init {
        viewModelScope.launch {
            val period = DateTimeConverter().getPeriod(0)
            settingsRepository.getBudget(period.month, period.year)?.filterNotNull()
                ?.collect {
                    _settings.value = it
                }
        }
    }

    fun updateSettings(settings: TotalBudgetEntity) {
        viewModelScope.launch {
            settingsRepository.setBudget(settings)
        }
    }
}
