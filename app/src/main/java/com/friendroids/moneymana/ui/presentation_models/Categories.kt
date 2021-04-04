package com.friendroids.moneymana.ui.presentation_models

import androidx.room.Entity

data class Categories(
    val id: Long?,
    val image_category: Int,
    val title_category: String,
    val month_budget: Int,
    val year_budget: Int,
    val sum_spent: Double,
    val sum_remained: Double,
    val sum_maximum: Int
)