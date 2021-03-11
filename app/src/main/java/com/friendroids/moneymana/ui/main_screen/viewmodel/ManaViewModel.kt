package com.friendroids.moneymana.ui.main_screen.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.friendroids.moneymana.domain.repository.ManaRepository
import com.friendroids.moneymana.ui.presentation_models.ManaCategory
import kotlinx.coroutines.launch

class ManaViewModel(private val manaRepository: ManaRepository) : ViewModel() {

    private val _manaCategories = MutableLiveData<List<ManaCategory>>()
    val manaCategories: LiveData<List<ManaCategory>> get() = _manaCategories

    fun updateManaProgress(manaCategory: ManaCategory) {
        viewModelScope.launch {
            _manaCategories.value = _manaCategories.value?.map {
                if (it.title == manaCategory.title) it.copy(sumRemained = manaCategory.sumRemained) else it
            }
        }
    }

    fun getUserManaState() {
        _manaCategories.value = manaRepository.getMana()
    }
}
