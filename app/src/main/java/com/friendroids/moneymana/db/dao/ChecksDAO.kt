package com.friendroids.moneymana.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.friendroids.moneymana.db.models.CheckEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChecksDAO {
    @Query("SELECT * FROM checks ORDER BY _id ASC")
    fun getAll(): Flow<List<CheckEntity>>

    @Query("SELECT * FROM checks WHERE id_category == :idCategory ORDER BY _id ASC")
    fun getAllFromCategory(idCategory: Long): Flow<List<CheckEntity>>

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


/*
    @Query(
        "SELECT checks.*, categories.title AS category_title, categories.image AS category_image " +
                "FROM checks, categories " +
                "WHERE checks.categorieid = categories._id " +
                "AND (checks.datecheck between :dateStart and :dateEnd) AND checks.categorieid = :categorieId"
    )
    fun getCheckCategorieByDate(
        dateStart: Date,
        dateEnd: Date,
        categorieId: Int
    ): Flow<List<CheckCategorie>>
*/

}