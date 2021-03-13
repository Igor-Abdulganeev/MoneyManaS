package com.friendroids.moneymana.data.repository

import com.friendroids.moneymana.db.DBContract.TotalBudget.TOTAL_BUDGET_PRIMARY_KEY
import com.friendroids.moneymana.db.DataBase
import com.friendroids.moneymana.db.models.TotalBudgetEntity
import com.friendroids.moneymana.domain.repository.SettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class SettingsRepositoryImpl(private val db: DataBase) : SettingsRepository {

    override fun getPrimaryBudgetSettings(): Flow<TotalBudgetEntity> =
        db.budgetParametersDAO.getTotalBudget(TOTAL_BUDGET_PRIMARY_KEY)

    override suspend fun updatePrimaryBudgetSettings(totalBudgetEntity: TotalBudgetEntity) =
        withContext(Dispatchers.IO) {
            db.budgetParametersDAO.updateTotalBudget(totalBudgetEntity)
        }
}