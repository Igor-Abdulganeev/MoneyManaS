package com.friendroids.moneymana.db.models

import androidx.room.ColumnInfo
import com.friendroids.moneymana.db.DBContract

class BudgetParameterCategory(
        @ColumnInfo(name = DBContract.BudgetParameters.COLUMN_NAME_ID)
        val _id: Long,
        @ColumnInfo(name = DBContract.BudgetParameters.COLUMN_NAME_ID_CATEGORY)
        val idCategory: Long,
        @ColumnInfo(name = DBContract.BudgetParameters.COLUMN_NAME_DATE_BUDGET)
        val dateBudget: Long,
        @ColumnInfo(name = DBContract.BudgetParameters.COLUMN_NAME_SUM_BUDGET)
        val sumBudget: Double,
        @ColumnInfo(name = DBContract.BudgetParameters.COLUMN_NAME_SUM_FACT)
        val sumFact: Double,
        @ColumnInfo(name = DBContract.Categories.COLUMN_NAME_TITLE)
        val titleCategory: String,
        @ColumnInfo(name = DBContract.Categories.COLUMN_NAME_ID_IMAGE)
        val imageIdCategory: Int
)
