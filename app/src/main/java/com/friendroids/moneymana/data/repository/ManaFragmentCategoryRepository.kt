package com.friendroids.moneymana.data.repository

import com.friendroids.moneymana.db.DataBase
import com.friendroids.moneymana.db.models.CategoryEntity
import com.friendroids.moneymana.db.models.CheckEntity
import kotlinx.coroutines.flow.Flow

class ManaFragmentCategoryRepository(private val db: DataBase) {

    fun getChekcsCategory(idCategory: Long): Flow<List<CheckEntity>> = db.checksDAO.getAllFromCategory(idCategory)

    fun getCategory(idCategory: Long): Flow<CategoryEntity> = db.categoriesDAO.getCategoriesById(idCategory)

}