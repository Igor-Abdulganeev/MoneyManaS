package com.friendroids.moneymana.ui.presentation_models

data class Check(
        val id: Long,
        val dateCheck: Long,
        val category: Category,
        val sumCheck: Double,
        val fnCheck: Long,
        val iCheck: Long,
        val fpCheck: Long
)
