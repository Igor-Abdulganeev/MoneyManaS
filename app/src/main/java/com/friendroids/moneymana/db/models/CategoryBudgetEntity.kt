package com.friendroids.moneymana.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.friendroids.moneymana.db.DBContract

@Entity(tableName = DBContract.CategoryBudget.TABLE_NAME)
data class CategoryBudgetEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DBContract.CategoryBudget.COLUMN_NAME_ID)
    val id: Long? = null,
    @ColumnInfo(name = DBContract.CategoryBudget.COLUMN_NAME_ID_CATEGORY)
    val id_category: Long,
    @ColumnInfo(name = DBContract.CategoryBudget.COLUMN_NAME_MONTH)
    val month: Int,
    @ColumnInfo(name = DBContract.CategoryBudget.COLUMN_NAME_YEAR)
    val year: Int,
    @ColumnInfo(name = DBContract.CategoryBudget.COLUMN_NAME_SUM_SPENT)
    val sumSpent: Double,
    @ColumnInfo(name = DBContract.CategoryBudget.COLUMN_NAME_SUM_REMAINED)
    val sumRemained: Double,
    @ColumnInfo(name = DBContract.CategoryBudget.COLUMN_NAME_SUM_MAXIMUM)
    val sumMaximum: Int
)