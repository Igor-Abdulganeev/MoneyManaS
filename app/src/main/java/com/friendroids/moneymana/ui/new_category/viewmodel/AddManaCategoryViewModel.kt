package com.friendroids.moneymana.ui.new_category.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.friendroids.moneymana.domain.repository.ManaRepository
import com.friendroids.moneymana.ui.presentation_models.ManaCategory
import kotlinx.coroutines.launch

class AddManaCategoryViewModel(private val manaRepository: ManaRepository) : ViewModel() {

    fun insertToDataBase(manaCategory: ManaCategory) {
        viewModelScope.launch {
            manaRepository.insertManaCategory(manaCategory)
        }
    }
}