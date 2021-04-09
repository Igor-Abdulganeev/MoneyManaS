package com.friendroids.moneymana.db

object DBContract {
    const val DATABASE_NAME = "Purchase.db"

    object CategoriesTable {
        const val TABLE_NAME = "categories"
        const val COLUMN_NAME_ID = "id"
        const val COLUMN_NAME_ID_IMAGE = "image_category"
        const val COLUMN_NAME_TITLE = "title_category"
        const val COLUMN_NAME_ACTIVE = "active"
    }

    object Checks {
        const val TABLE_NAME = "checks"
        const val COLUMN_NAME_ID = "id"
        const val COLUMN_NAME_DATE = "date_check"
        const val COLUMN_NAME_ID_CATEGORY = "id_category"
        const val COLUMN_NAME_SUM = "sum_check"
        const val COLUMN_NAME_FN = "fn_check"
        const val COLUMN_NAME_I = "i_check"
        const val COLUMN_NAME_FP = "fp_check"
    }

    object TotalBudget {
        const val TABLE_NAME = "total_budget"
        const val COLUMN_NAME_ID = "id"
        const val COLUMN_NAME_MONTH = "month_budget"
        const val COLUMN_NAME_YEAR = "year_budget"
        const val COLUMN_NAME_DAY_RENEWAL = "day_renewal"
        const val COLUMN_NAME_SUM_BUDGET = "sum_budget"
    }

    object CategoryBudget {
        const val TABLE_NAME = "category_budget"
        const val COLUMN_NAME_ID = "id"
        const val COLUMN_NAME_ID_CATEGORY = "id_category"
        const val COLUMN_NAME_MONTH = "month_budget"
        const val COLUMN_NAME_YEAR = "year_budget"
        const val COLUMN_NAME_SUM_SPENT = "sum_spent"
        const val COLUMN_NAME_SUM_REMAINED = "sum_remained"
        const val COLUMN_NAME_SUM_MAXIMUM = "sum_maximum"
    }
}