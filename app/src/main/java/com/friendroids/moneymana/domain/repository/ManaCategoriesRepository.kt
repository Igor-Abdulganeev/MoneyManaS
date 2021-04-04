package com.friendroids.moneymana.domain.repository

import com.friendroids.moneymana.ui.presentation_models.Check
import com.friendroids.moneymana.ui.presentation_models.ManaCategory
import kotlinx.coroutines.flow.Flow

interface ManaCategoriesRepository {

    fun getManaCategories(): Flow<List<ManaCategory>>

    fun getListCategory(): Flow<List<ManaCategory>> //List<ManaCategory>

    suspend fun insertCheck(check: Check)

//    suspend fun insertManaCategory(manaCategory: ManaCategory)
}
