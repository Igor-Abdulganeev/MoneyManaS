package com.friendroids.moneymana.db

import android.provider.BaseColumns

object DBContract {
    const val DATABASE_NAME = "Purchase.db"

    object Categories {
        const val TABLE_NAME = "categories"
        const val COLUMN_NAME_ID = BaseColumns._ID
        const val COLUMN_NAME_ID_IMAGE = "image_category"
        const val COLUMN_NAME_TITLE = "title_category"
        const val COLUMN_NAME_SUM_REMAINED = "sum_remained"
        const val COLUMN_NAME_SUM_MAXIMUM = "sum_maximum"
    }

    object Checks {
        const val TABLE_NAME = "checks"
        const val COLUMN_NAME_ID = BaseColumns._ID
        const val COLUMN_NAME_DATE = "date_check"
        const val COLUMN_NAME_ID_CATEGORY = "id_category"
        const val COLUMN_NAME_SUM = "sum_check"
        const val COLUMN_NAME_FN = "fn_check"
        const val COLUMN_NAME_I = "i_check"
        const val COLUMN_NAME_FP = "fp_check"
    }

    object BudgetParameters {
        const val TABLE_NAME = "budgetparameters"
        const val COLUMN_NAME_ID = BaseColumns._ID
        const val COLUMN_NAME_DATE_BUDGET = "date_budget"
        const val COLUMN_NAME_ID_CATEGORY = "id_category"
        const val COLUMN_NAME_SUM_BUDGET = "sum_budget"
        const val COLUMN_NAME_SUM_FACT = "sum_fact"
    }

    object TotalBudget {
        const val TABLE_NAME = "total_budget"
        const val COLUMN_ID = "id"
        const val TOTAL_BUDGET_PRIMARY_KEY = "TOTAL_BUDGET_PRIMARY_KEY"
    }
}