package com.friendroids.moneymana.ui

import androidx.annotation.DrawableRes

interface NavigationActivity {
    fun setImageResource(@DrawableRes resId: Int)
    fun openCategoryFragment(idCategory: Long)
}
