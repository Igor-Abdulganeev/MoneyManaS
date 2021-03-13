package com.friendroids.moneymana.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.friendroids.moneymana.db.DBContract.TotalBudget.COLUMN_ID
import com.friendroids.moneymana.db.DBContract.TotalBudget.TOTAL_BUDGET_PRIMARY_KEY
import com.friendroids.moneymana.db.DBContract.TotalBudget.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class TotalBudgetEntity(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_ID)
    val id: String = TOTAL_BUDGET_PRIMARY_KEY,
    val sum: Int,
    val daysTillRestartCount: Int
)
