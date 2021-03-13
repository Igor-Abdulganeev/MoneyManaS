package com.friendroids.moneymana.db.models

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface ChecksDAO {
    @Query("SELECT * FROM checks ORDER BY _id ASC")
    fun getAll(): Flow<List<CheckEntity>>

    @Query("SELECT * FROM checks WHERE categorieId == :idCategory ORDER BY _id ASC")
    fun getAlltoCategory(idCategory: Int): Flow<List<CheckEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(checkEntity: CheckEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(checkEntityList: List<CheckEntity>)

    @Query("DELETE FROM checks WHERE _id == :id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM checks")
    suspend fun deleteAll()

    @Query("SELECT * FROM checks WHERE _id == :id")
    fun getCheckById(id: Int): CheckEntity

    @Query(
        "SELECT checks.*, categories.title AS categorietitle, categories.image AS categorieimageid " +
                "FROM checks, categories " +
                "WHERE checks.categorieid = categories._id " +
                "AND (checks.datecheck between :dateStart and :dateEnd) AND checks.categorieid = :categorieId"
    )
    fun getCheckCategorieByDate(
        dateStart: Date,
        dateEnd: Date,
        categorieId: Int
    ): Flow<List<CheckCategorie>>
}