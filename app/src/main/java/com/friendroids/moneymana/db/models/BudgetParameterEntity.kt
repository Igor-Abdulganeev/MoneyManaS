package com.friendroids.moneymana.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.friendroids.moneymana.db.DBContract
import java.util.*

@Entity(
        tableName = DBContract.BudgetParameters.TABLE_NAME,
        indices = [Index(DBContract.BudgetParameters.COLUMN_NAME_ID)]
)
class BudgetParameterEntity (
        @PrimaryKey(autoGenerate = false)
        @ColumnInfo(name = DBContract.BudgetParameters.COLUMN_NAME_ID)
        val _id: Int,

        @ColumnInfo(name = DBContract.BudgetParameters.COLUMN_NAME_CATEGORIE_ID)
        val categorieId: Int,

        @ColumnInfo(name = DBContract.BudgetParameters.COLUMN_NAME_DATE_BUDGET)
        val dateBudget: Date,

        @ColumnInfo(name = DBContract.BudgetParameters.COLUMN_NAME_SUMMA_PLAN)
        val summaPlan: Double,

        @ColumnInfo(name = DBContract.BudgetParameters.COLUMN_NAME_SUMMA_FACT)
        val summaFact: Double
)