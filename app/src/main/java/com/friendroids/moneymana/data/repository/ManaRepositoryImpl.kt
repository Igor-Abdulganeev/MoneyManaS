package com.friendroids.moneymana.data.repository

import com.friendroids.moneymana.R
import com.friendroids.moneymana.db.DataBase
import com.friendroids.moneymana.db.models.CategorieEntity
import com.friendroids.moneymana.domain.repository.ManaRepository
import com.friendroids.moneymana.ui.presentation_models.ManaCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class ManaRepositoryImpl(private val db: DataBase) : ManaRepository {

    //temporary
    private val defautManaCategories = listOf(
        ManaCategory(100, "Total budget", 87900, 100000, R.drawable.food),
        ManaCategory(101, "Foods", 10000, 10000, R.drawable.food),
        ManaCategory(102, "Fuel", 2500, 5000, R.drawable.food_fork_drink),
        ManaCategory(103, "Clothes", 800, 8000, R.drawable.food),
        ManaCategory(104, "Other", 8000, 10000, R.drawable.food_fork_drink)
    )

    //todo get from database
    override fun getManaCategories(): Flow<List<ManaCategory>> =
        db.categoriesDAO.getAll().map { convertMana(it) } ?: flow {  emit(defautManaCategories) }

    override fun insertManaCategory(manaCategory: ManaCategory) {
        TODO("Not yet implemented")
    }

    private fun convertMana(list: List<CategorieEntity>): List<ManaCategory> =
        list.map {
            ManaCategory(
                id = it._id,
                title = it.title,
                sumRemained = it.sumRemained,
                maxSum = it.maxSum,
                imageId = it.imageId
            )
        }
}
