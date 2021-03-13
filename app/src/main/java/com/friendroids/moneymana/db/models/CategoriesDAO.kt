package com.friendroids.moneymana.db.models

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoriesDAO {
    @Query("SELECT * FROM categories ORDER BY _id ASC")
    fun getAll(): Flow<List<CategorieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(actorEntity: CategorieEntity)

    @Query("DELETE FROM categories WHERE _id == :id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM categories")
    suspend fun deleteAll()

    @Query("SELECT * FROM categories WHERE _id == :id")
    fun getCategoriesById(id: Int): CategorieEntity
}