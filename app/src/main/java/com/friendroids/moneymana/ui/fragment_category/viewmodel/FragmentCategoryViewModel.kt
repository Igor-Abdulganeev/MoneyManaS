package com.friendroids.moneymana.ui.fragment_category.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.friendroids.moneymana.R
import com.friendroids.moneymana.domain.repository.ManaRepository
import com.friendroids.moneymana.ui.presentation_models.ManaCategory
import kotlinx.coroutines.launch

class FragmentCategoryViewModel(private val manaRepository: ManaRepository) : ViewModel() {

    private val _manaCategories = MutableLiveData<ManaCategory>()
    val manaCategories: LiveData<ManaCategory> get() = _manaCategories

    fun updateManaProgress(categoryId: Int) {
        viewModelScope.launch {
            _manaCategories.value = ManaCategory(
                title="Fuel",
                sumRemained=2500,
                maxSum = 5000,
                imageId = R.drawable.food_fork_drink
            )
        }
    }

}