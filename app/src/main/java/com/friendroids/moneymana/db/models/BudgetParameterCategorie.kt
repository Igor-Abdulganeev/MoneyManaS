package com.friendroids.moneymana.db.models

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.friendroids.moneymana.db.DBContract
import java.util.*

class BudgetParameterCategorie(
        @ColumnInfo(name = DBContract.BudgetParameters.COLUMN_NAME_ID)
        val _id: Int,

        @ColumnInfo(name = DBContract.BudgetParameters.COLUMN_NAME_CATEGORIE_ID)
        val categorieId: Int,

        @ColumnInfo(name = DBContract.BudgetParameters.COLUMN_NAME_DATE_BUDGET)
        val dateBudget: Date,

        @ColumnInfo(name = DBContract.BudgetParameters.COLUMN_NAME_SUMMA_PLAN)
        val summaPlan: Double,

        @ColumnInfo(name = DBContract.BudgetParameters.COLUMN_NAME_SUMMA_FACT)
        val summaFact: Double,

         @ColumnInfo(name = "categorietitle")
         val categorieTitle:String,
            @ColumnInfo(name = "categorieimageid")
            val categorieImageId:Int
)
