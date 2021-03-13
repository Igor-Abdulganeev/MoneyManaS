package com.friendroids.moneymana.db

import android.provider.BaseColumns

object DBContract {
    const val DATABASE_NAME = "Purchase.db"

    object Categories {
        const val TABLE_NAME = "categories"

        const val COLUMN_NAME_ID = BaseColumns._ID
        const val COLUMN_NAME_IMAGEID = "image"
        const val COLUMN_NAME_TITLE = "title"
    }

    object Checks {
        const val TABLE_NAME = "checks"

        const val COLUMN_NAME_ID = BaseColumns._ID
        const val COLUMN_NAME_DATE_CHECK = "datecheck"
        const val COLUMN_NAME_CATEGORIE_ID = "categorieid"
        const val COLUMN_NAME_SUMMA = "summa"
    }

    object BudgetParameters {
        const val TABLE_NAME = "budgetparameters"

        const val COLUMN_NAME_ID = BaseColumns._ID
        const val COLUMN_NAME_DATE_BUDGET = "datebudget"
        const val COLUMN_NAME_CATEGORIE_ID = "categorieid"
        const val COLUMN_NAME_SUMMA_PLAN = "summaplan"
        const val COLUMN_NAME_SUMMA_FACT = "summafact"
    }

    object TotalBudget {
        const val TABLE_NAME = "total_budget"
        const val COLUMN_ID = "id"
        const val TOTAL_BUDGET_PRIMARY_KEY = "TOTAL_BUDGET_PRIMARY_KEY"
    }
}