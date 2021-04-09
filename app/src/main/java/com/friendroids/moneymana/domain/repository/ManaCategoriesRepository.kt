package com.friendroids.moneymana.domain.repository

import com.friendroids.moneymana.db.models.CheckEntity
import com.friendroids.moneymana.ui.presentation_models.Check
import com.friendroids.moneymana.ui.presentation_models.ManaCategory
import kotlinx.coroutines.flow.Flow

interface ManaCategoriesRepository {

    fun getManaCategories(): Flow<List<ManaCategory>>

    fun getListCategory(): Flow<List<ManaCategory>> //List<ManaCategory>

    suspend fun insertCheck(check: Check)

    fun getCategoryBudget(idCategory: Long): Flow<List<CheckEntity>>?

    fun loadChecks(idCategory: Long): Flow<List<CheckEntity>>?

//    suspend fun insertManaCategory(manaCategory: ManaCategory)
}
