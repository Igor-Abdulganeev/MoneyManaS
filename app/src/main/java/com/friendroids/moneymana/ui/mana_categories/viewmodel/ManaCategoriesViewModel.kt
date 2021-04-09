package com.friendroids.moneymana.ui.mana_categories.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.friendroids.moneymana.db.models.TotalBudgetEntity
import com.friendroids.moneymana.domain.repository.ManaCategoriesRepository
import com.friendroids.moneymana.domain.repository.SettingsRepository
import com.friendroids.moneymana.ui.presentation_models.ManaCategory
import com.friendroids.moneymana.utils.extensions.DateTimeConverter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

class ManaCategoriesViewModel(
    private val manaCategoriesRepository: ManaCategoriesRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _manaCategories = MutableLiveData<List<ManaCategory>>()
    private val _primarySettings = MutableLiveData<TotalBudgetEntity>()
    val manaCategories: LiveData<List<ManaCategory>> get() = _manaCategories
    val primarySettings: LiveData<TotalBudgetEntity> get() = _primarySettings

/*
    fun updateManaProgress(manaCategory: ManaCategory) {
        viewModelScope.launch {
            _manaCategories.value = _manaCategories.value?.map {
                withContext(Dispatchers.IO) {
                    if (it.title == manaCategory.title) it.copy(sumRemained = manaCategory.sumRemained) else it
                }
            }
        }
    }
*/

    fun getUserManaState() {
        viewModelScope.launch {
            launch {
                manaCategoriesRepository.getManaCategories()
                    .filterNotNull()
                    .collect {
                        _manaCategories.value = it
                    }
            }
            val period = DateTimeConverter().getPeriod(0)
            settingsRepository.getBudget(period.month, period.year)?.filterNotNull()
                // manaRepository.getPrimaryBudgetSettings()
                ?.collect {
                    Log.d("CameraFragment", "settingsRepository.getBudget $it")
                    _primarySettings.value = it
                }
        }
    }
}
