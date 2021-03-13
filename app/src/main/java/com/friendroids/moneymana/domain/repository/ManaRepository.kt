package com.friendroids.moneymana.domain.repository

import com.friendroids.moneymana.ui.presentation_models.ManaCategory
import kotlinx.coroutines.flow.Flow

interface ManaRepository {

    fun getManaCategories(): Flow<List<ManaCategory>>

    suspend fun getListCategory(): List<ManaCategory>

    suspend fun insertManaCategory(manaCategory: ManaCategory)
}
