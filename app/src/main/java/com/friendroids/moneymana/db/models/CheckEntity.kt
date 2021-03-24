package com.friendroids.moneymana.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.friendroids.moneymana.db.DBContract

@Entity(
        tableName = DBContract.Checks.TABLE_NAME,
        indices = [Index(DBContract.Checks.COLUMN_NAME_ID)]
)
class CheckEntity(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = DBContract.Checks.COLUMN_NAME_ID)
        val id: Long? = null,
        @ColumnInfo(name = DBContract.Checks.COLUMN_NAME_ID_CATEGORY)
        val idCategory: Long,
        @ColumnInfo(name = DBContract.Checks.COLUMN_NAME_DATE)
        val dateCheck: Long,
        @ColumnInfo(name = DBContract.Checks.COLUMN_NAME_SUM)
        val sumCheck: Double,
        @ColumnInfo(name = DBContract.Checks.COLUMN_NAME_FN)
        val fnCheck: Long,
        @ColumnInfo(name = DBContract.Checks.COLUMN_NAME_I)
        val iCheck: Long,
        @ColumnInfo(name = DBContract.Checks.COLUMN_NAME_FP)
        val fpCheck: Long
)