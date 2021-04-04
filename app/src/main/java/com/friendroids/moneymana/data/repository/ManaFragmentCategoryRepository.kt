package com.friendroids.moneymana.data.repository

import com.friendroids.moneymana.db.ManaDatabase
import com.friendroids.moneymana.db.models.CheckEntity
import kotlinx.coroutines.flow.Flow

class ManaFragmentCategoryRepository(private val db: ManaDatabase) {

    fun getChekcsCategory(idCategory: Int): Flow<List<CheckEntity>> =
        db.checksDAO.getAllFromCategory(idCategory)
    //для залива
}