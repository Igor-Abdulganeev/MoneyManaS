package com.friendroids.moneymana.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.friendroids.moneymana.db.DBContract

@Entity(
        tableName = DBContract.BudgetParameters.TABLE_NAME,
        indices = [Index(DBContract.BudgetParameters.COLUMN_NAME_ID)]
)
class BudgetParameterEntity(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = DBContract.BudgetParameters.COLUMN_NAME_ID)
        val _id: Long,
        @ColumnInfo(name = DBContract.BudgetParameters.COLUMN_NAME_ID_CATEGORY)
        val idCategory: Long,
        @ColumnInfo(name = DBContract.BudgetParameters.COLUMN_NAME_DATE_BUDGET)
        val dateBudget: Long,
        @ColumnInfo(name = DBContract.BudgetParameters.COLUMN_NAME_SUM_BUDGET)
        val sumBudget: Double,
        @ColumnInfo(name = DBContract.BudgetParameters.COLUMN_NAME_SUM_FACT)
        val sumFact: Double
)