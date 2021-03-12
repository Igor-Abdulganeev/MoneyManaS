package com.friendroids.moneymana.db.models

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ChecksDAO {
    @Query("SELECT * FROM checks ORDER BY _id ASC")
    suspend fun getAll(): List<CheckEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(checkEntity: CheckEntity)

    @Query("DELETE FROM checks WHERE _id == :id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM checks")
    suspend fun deleteAll()

    @Query("SELECT * FROM checks WHERE _id == :id")
    fun getCheckById(id: Int): CheckEntity
}