package com.friendroids.moneymana.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.friendroids.moneymana.db.DBContract

@Entity(
        tableName = DBContract.Categories.TABLE_NAME,
        indices = [Index(DBContract.Categories.COLUMN_NAME_ID)]
)
class CategoryEntity(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = DBContract.Categories.COLUMN_NAME_ID)
        val _id: Long? = null,
        @ColumnInfo(name = DBContract.Categories.COLUMN_NAME_ID_IMAGE)
        val imageId: Int,
        @ColumnInfo(name = DBContract.Categories.COLUMN_NAME_TITLE)
        val title: String,
        @ColumnInfo(name = DBContract.Categories.COLUMN_NAME_SUM_REMAINED)
        val sumRemained: Int,
        @ColumnInfo(name = DBContract.Categories.COLUMN_NAME_SUM_MAXIMUM)
        val sumMaximum: Int
)