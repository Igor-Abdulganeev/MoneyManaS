package com.friendroids.moneymana.ui.presentation_models

data class BudgetParameter(
        val id: Long,
        val dateBudget: Long,
        val category: Category,
        val sumBudget: Double,
        val sumFact: Double
)