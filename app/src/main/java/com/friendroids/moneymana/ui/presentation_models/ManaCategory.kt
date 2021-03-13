package com.friendroids.moneymana.ui.presentation_models

data class ManaCategory(
    val id: Int? = null,
    val title: String,
    val sumRemained: Int,
    val maxSum: Int,
    val imageId: Int = 0
) {
    val percentRemained: Int = sumRemained.getPercentage()

    private fun Int.getPercentage() = times(100).div(maxSum)
}
