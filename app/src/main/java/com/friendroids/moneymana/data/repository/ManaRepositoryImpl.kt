package com.friendroids.moneymana.data.repository

import com.friendroids.moneymana.db.DBContract.TotalBudget.TOTAL_BUDGET_PRIMARY_KEY
import com.friendroids.moneymana.db.DataBase
import com.friendroids.moneymana.db.models.CategoryEntity
import com.friendroids.moneymana.db.models.CheckEntity
import com.friendroids.moneymana.db.models.TotalBudgetEntity
import com.friendroids.moneymana.domain.repository.ManaRepository
import com.friendroids.moneymana.ui.presentation_models.ManaCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.withContext

class ManaRepositoryImpl(private val db: DataBase) : ManaRepository {

    override fun getPrimaryBudgetSettings(): Flow<TotalBudgetEntity> =
        db.budgetParametersDAO.getTotalBudget(TOTAL_BUDGET_PRIMARY_KEY)

    override fun getManaCategories(): Flow<List<ManaCategory>> =
            db.categoriesDAO.getAll()
                    .combine(db.checksDAO.getAll()) { categories: List<CategoryEntity>, checks: List<CheckEntity> ->
                        convertListMana(categories, checks)
                    }

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
                    sumMaximum = it.sumMaximum,
                    imageId = it.imageId
            )
        }
    }

    private fun convertListMana(
            categories: List<CategoryEntity>,
            checks: List<CheckEntity>
    ): List<ManaCategory> =
            categories.map { category ->
                val sumChecks =
                        checks.filter { it.idCategory == category._id }.sumByDouble { it.sumCheck }
                val sumRemained = category.sumMaximum - sumChecks
                ManaCategory(
                        id = category._id,
                        title = category.title,
                        sumRemained = sumRemained.toInt(),
                        sumMaximum = category.sumMaximum,
                        imageId = category.imageId
                )
        }

    private fun ManaCategory.convertMana() =
            CategoryEntity(
                    title = title,
                    sumMaximum = sumMaximum,
                    sumRemained = sumRemained,
                    imageId = imageId
            )
}
