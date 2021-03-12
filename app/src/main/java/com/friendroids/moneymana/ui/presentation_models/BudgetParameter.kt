package com.friendroids.moneymana.ui.presentation_models

import java.util.*

data class BudgetParameter(
        val id:Int,
        val dateBudget:Date,
        val categorie: Categorie,
        val summaPlan:Double,
        val summaFact:Double
)