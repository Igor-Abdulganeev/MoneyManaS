package com.friendroids.moneymana.data.repository

import com.friendroids.moneymana.db.DBContract.TotalBudget.TOTAL_BUDGET_PRIMARY_KEY
import com.friendroids.moneymana.db.DataBase
import com.friendroids.moneymana.db.models.CategorieEntity
import com.friendroids.moneymana.db.models.TotalBudgetEntity
import com.friendroids.moneymana.domain.repository.ManaRepository
import com.friendroids.moneymana.ui.presentation_models.ManaCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class ManaRepositoryImpl(private val db: DataBase) : ManaRepository {

    override fun getPrimaryBudgetSettings(): Flow<TotalBudgetEntity> =
        db.budgetParametersDAO.getTotalBudget(TOTAL_BUDGET_PRIMARY_KEY)

    override fun getManaCategories(): Flow<List<ManaCategory>> =
        db.categoriesDAO.getAll().map { convertListMana(it) }

    override suspend fun insertManaCategory(manaCategory: ManaCategory) =
        withContext(Dispatchers.IO) {
            db.categoriesDAO.insert(manaCategory.convertMana())
        }

    override suspend fun getListCategory(): List<ManaCategory> = withContext(Dispatchers.IO) {
        db.categoriesDAO.getListAll().map {
            ManaCategory(
                id = it._id,
                title = it.title,
                sumRemained = it.sumRemained,
                maxSum = it.maxSum,
                imageId = it.imageId
            )
        }
    }

    private fun convertListMana(list: List<CategorieEntity>): List<ManaCategory> =
        list.map {
            ManaCategory(
                id = it._id,
                title = it.title,
                sumRemained = it.sumRemained,
                maxSum = it.maxSum,
                imageId = it.imageId
            )
        }

    private fun ManaCategory.convertMana() =
        CategorieEntity(
            title = title,
            maxSum = maxSum,
            sumRemained = sumRemained,
            imageId = imageId
        )
}
