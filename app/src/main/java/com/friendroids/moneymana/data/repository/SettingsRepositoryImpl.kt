package com.friendroids.moneymana.data.repository

import com.friendroids.moneymana.db.ManaDatabase
import com.friendroids.moneymana.db.models.TotalBudgetEntity
import com.friendroids.moneymana.domain.repository.SettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class SettingsRepositoryImpl(private val db: ManaDatabase) : SettingsRepository {

    //перенести в сеттинг, так же надо SingleFlow
    override fun getBudget(month: Int, year: Int): Flow<TotalBudgetEntity> =
        db.budgetDAO.getTotalBudget(month, year)

    override suspend fun setBudget(totalBudgetEntity: TotalBudgetEntity) =
        withContext(Dispatchers.IO) {
            db.budgetDAO.updateTotalBudget(totalBudgetEntity)
        }
/*
    override fun getPrimaryBudgetSettings(): Flow<TotalBudgetEntity> =
        db.budgetParametersDAO.getTotalBudget(TOTAL_BUDGET_PRIMARY_KEY)
*/

/*
    override suspend fun updatePrimaryBudgetSettings(totalBudgetEntity: TotalBudgetEntity) =
        withContext(Dispatchers.IO) {
            db.budgetParametersDAO.updateTotalBudget(totalBudgetEntity)
        }
*/
}