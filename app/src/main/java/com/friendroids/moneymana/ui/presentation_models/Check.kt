package com.friendroids.moneymana.ui.presentation_models

import java.util.*

data class Check (
        val id:Int,
        val dateCheck:Date,
        val categorie: Categorie,
        val summa:Int
        )
