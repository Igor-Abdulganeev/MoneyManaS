package com.friendroids.moneymana.data.repository

import com.friendroids.moneymana.db.DataBase
import com.friendroids.moneymana.db.models.CategorieEntity
import com.friendroids.moneymana.domain.repository.ManaRepository
import com.friendroids.moneymana.ui.presentation_models.ManaCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ManaRepositoryImpl(private val db: DataBase) : ManaRepository {

    override fun getManaCategories(): Flow<List<ManaCategory>> =
        db.categoriesDAO.getAll().map { convertMana(it) }

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
