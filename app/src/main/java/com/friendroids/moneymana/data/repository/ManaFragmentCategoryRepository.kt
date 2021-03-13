package com.friendroids.moneymana.data.repository

import com.friendroids.moneymana.db.DataBase
import com.friendroids.moneymana.db.models.CheckEntity
import kotlinx.coroutines.flow.Flow

class ManaFragmentCategoryRepository(private val db: DataBase) {

    fun getChekcsCategory(idCategory: Int): Flow<List<CheckEntity>> = db.checksDAO.getAlltoCategory(idCategory)

}