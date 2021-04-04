package com.friendroids.moneymana.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.friendroids.moneymana.db.DBContract

@Entity(tableName = DBContract.TotalBudget.TABLE_NAME)
data class TotalBudgetEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DBContract.TotalBudget.COLUMN_NAME_ID)
    val id: Long? = null,
    @ColumnInfo(name = DBContract.TotalBudget.COLUMN_NAME_MONTH)
    val month: Int,
    @ColumnInfo(name = DBContract.TotalBudget.COLUMN_NAME_YEAR)
    val year: Int,
    @ColumnInfo(name = DBContract.TotalBudget.COLUMN_NAME_DAY_RENEWAL)
    val dayRestart: Int,
    @ColumnInfo(name = DBContract.TotalBudget.COLUMN_NAME_SUM_BUDGET)
    val sumBudget: Int
)
