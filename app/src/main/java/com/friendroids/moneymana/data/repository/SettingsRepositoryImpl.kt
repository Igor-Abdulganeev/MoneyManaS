package com.friendroids.moneymana.data.repository

import androidx.room.withTransaction
import com.friendroids.moneymana.db.ManaDatabase
import com.friendroids.moneymana.db.models.TotalBudgetEntity
import com.friendroids.moneymana.domain.repository.SettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class SettingsRepositoryImpl(private val db: ManaDatabase) : SettingsRepository {

    override fun getBudget(month: Int, year: Int): Flow<TotalBudgetEntity>? =
        db.budgetDAO.getTotalBudget(month, year)

    override suspend fun setBudget(totalBudgetEntity: TotalBudgetEntity) =
        withContext(Dispatchers.IO) {
            db.withTransaction {
                with(totalBudgetEntity) {
                    db.budgetDAO.insertTotalBudget(this)
                    db.categoriesDAO.updateCategories(sumBudget, month, year)
                }
            }
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