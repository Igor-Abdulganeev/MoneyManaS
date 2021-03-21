package com.friendroids.moneymana.ui.presentation_models

import com.friendroids.moneymana.R

data class ManaCategory(
    val id: Int? = null,
    val title: String,
    val sumRemained: Int,
    val maxSum: Int,
    val imageId: Int = R.drawable.adjust
) {
    val percentRemained: Int = sumRemained.getPercentage()
    val status: Int = when (percentRemained) {
        in 20..50 -> R.color.shiny_yellow_green
        in 51..100 -> R.color.indian_green
        else -> R.color.pale_red
    }

    private fun Int.getPercentage() = times(100).div(maxSum)
}
