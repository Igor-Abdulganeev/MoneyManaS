package com.friendroids.moneymana.domain.repository

import com.friendroids.moneymana.db.models.TotalBudgetEntity
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    fun getPrimaryBudgetSettings(): Flow<TotalBudgetEntity>

    suspend fun updatePrimaryBudgetSettings(totalBudgetEntity: TotalBudgetEntity)
}