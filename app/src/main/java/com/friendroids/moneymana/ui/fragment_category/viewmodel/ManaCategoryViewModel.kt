package com.friendroids.moneymana.ui.fragment_category.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.friendroids.moneymana.db.models.CategoryBudgetEntity
import com.friendroids.moneymana.db.models.CheckEntity
import com.friendroids.moneymana.domain.repository.ManaCategoriesRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

class ManaCategoryViewModel(
    private val manaCategoriesRepository: ManaCategoriesRepository
) : ViewModel() {

    private var _manaChecks = MutableLiveData<List<CheckEntity>>()
    val manaChecks: LiveData<List<CheckEntity>> get() = _manaChecks

    private var _category = MutableLiveData<CategoryBudgetEntity>()
    val category: LiveData<CategoryBudgetEntity> get() = _category

    fun loadChecks(categoryId: Long) {
        viewModelScope.launch {
            manaCategoriesRepository.loadChecks(categoryId)
                ?.filterNotNull()
                ?.collect {
                    _manaChecks.value = it
                }
        }
    }

    fun loadCategoryBudget(categoryId: Long) {
        viewModelScope.launch {

        }
    }

}