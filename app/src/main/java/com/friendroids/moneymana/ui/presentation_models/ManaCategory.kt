package com.friendroids.moneymana.ui.presentation_models

import android.graphics.Color

data class ManaCategory(
    val title: String,
    val sumRemained: Int,
    val maxSum: Int,
    val imageId: Int = 0
) {
    val percentRemained: Int = sumRemained.getPercentage()
    val status: Int = when (percentRemained) {
        in 0..19 -> Color.RED
        in 20..50 -> Color.YELLOW
        else -> Color.GREEN
    }

    private fun Int.getPercentage() = times(100).div(maxSum)
}
