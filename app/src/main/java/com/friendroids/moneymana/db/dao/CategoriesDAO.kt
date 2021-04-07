package com.friendroids.moneymana.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.friendroids.moneymana.db.models.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoriesDAO {
    @Query("SELECT * FROM categories ORDER BY _id ASC")
    fun getAll(): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM categories ORDER BY _id ASC")
    suspend fun getListAll(): List<CategoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(categoryEntity: CategoryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(categoryEntityList: List<CategoryEntity>)

    @Query("DELETE FROM categories WHERE _id == :id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM categories")
    suspend fun deleteAll()

    @Query("SELECT * FROM categories WHERE _id == :id")
    fun getCategoriesById(id: Long): Flow<CategoryEntity>
}