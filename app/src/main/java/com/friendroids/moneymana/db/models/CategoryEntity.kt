package com.friendroids.moneymana.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.friendroids.moneymana.db.DBContract

@Entity(
    tableName = DBContract.CategoriesTable.TABLE_NAME,
    indices = [Index(DBContract.CategoriesTable.COLUMN_NAME_ID)]
)
class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DBContract.CategoriesTable.COLUMN_NAME_ID)
    val id: Long? = null,
    @ColumnInfo(name = DBContract.CategoriesTable.COLUMN_NAME_ID_IMAGE)
    val imageId: Int,
    @ColumnInfo(name = DBContract.CategoriesTable.COLUMN_NAME_TITLE)
    val categoryName: String,
    @ColumnInfo(name = DBContract.CategoriesTable.COLUMN_NAME_ACTIVE)
    val isActive: Boolean,
)