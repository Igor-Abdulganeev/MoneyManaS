package com.friendroids.moneymana.ui.fragment_category.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.friendroids.moneymana.data.repository.ManaFragmentCategoryRepository
import com.friendroids.moneymana.db.models.CategoryEntity
import com.friendroids.moneymana.db.models.CheckEntity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FragmentCategoryViewModel(private val manaFragmentCategoryRepository: ManaFragmentCategoryRepository) :
    ViewModel() {

    private var _manaChecks = MutableLiveData<List<CheckEntity>>()
    val manaChecks: LiveData<List<CheckEntity>> get() = _manaChecks

    private var _category = MutableLiveData<CategoryEntity>()
    val category: LiveData<CategoryEntity> get() = _category

    fun updateManaProgress(categoryId: Long) {
        viewModelScope.launch {
            manaFragmentCategoryRepository.getChekcsCategory(categoryId).collect {
                _manaChecks.value = it
            }
        }

        viewModelScope.launch {
            manaFragmentCategoryRepository.getCategory(categoryId).collect {
                _category.value = it
            }
        }

    }

    fun removeCheck(idCheck: Long) {
        viewModelScope.launch {
            manaFragmentCategoryRepository.onRemoveCheck(idCheck)
        }
    }
}