package com.friendroids.moneymana.domain.repository

import com.friendroids.moneymana.db.models.TotalBudgetEntity
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    fun getBudget(month: Int, year: Int): Flow<TotalBudgetEntity>?

    suspend fun setBudget(totalBudgetEntity: TotalBudgetEntity)
}