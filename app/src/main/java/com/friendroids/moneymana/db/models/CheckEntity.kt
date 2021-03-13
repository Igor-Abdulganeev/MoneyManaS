package com.friendroids.moneymana.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.friendroids.moneymana.db.DBContract
import java.util.*


@Entity(
        tableName = DBContract.Checks.TABLE_NAME,
        indices = [Index(DBContract.Checks.COLUMN_NAME_ID)]
)
class CheckEntity(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = DBContract.Checks.COLUMN_NAME_ID)
        val _id: Int,

        @ColumnInfo(name = DBContract.Checks.COLUMN_NAME_CATEGORIE_ID)
        val categorieId: Int,

        @ColumnInfo(name = DBContract.Checks.COLUMN_NAME_DATE_CHECK)
        val dateCheck: Date,

        @ColumnInfo(name = DBContract.Checks.COLUMN_NAME_SUMMA)
        val summa: Double
)