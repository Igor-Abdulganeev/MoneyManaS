package com.friendroids.moneymana.db.models

import androidx.room.ColumnInfo
import com.friendroids.moneymana.db.DBContract

class CheckCategory(
        @ColumnInfo(name = DBContract.Checks.COLUMN_NAME_ID)
        val _id: Long,
        @ColumnInfo(name = DBContract.Checks.COLUMN_NAME_ID_CATEGORY)
        val idCategory: Long,
        @ColumnInfo(name = DBContract.Checks.COLUMN_NAME_DATE)
        val dateCheck: Long,
        @ColumnInfo(name = DBContract.Checks.COLUMN_NAME_SUM)
        val sumCheck: Double,
        @ColumnInfo(name = DBContract.Categories.COLUMN_NAME_TITLE)
        val titleCategory: String,
        @ColumnInfo(name = DBContract.Categories.COLUMN_NAME_ID_IMAGE)
        val idImageCategory: Int
)
