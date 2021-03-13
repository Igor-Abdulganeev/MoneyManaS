package com.friendroids.moneymana.data.repository

import com.friendroids.moneymana.R
import com.friendroids.moneymana.domain.repository.ManaRepository
import com.friendroids.moneymana.ui.presentation_models.ManaCategory

class ManaRepositoryImpl: ManaRepository {

    //temporary
    private val defautManaCategories = listOf(
        ManaCategory("Total budget", 87900, 100000, R.drawable.food),
        ManaCategory("Foods", 10000, 10000, R.drawable.food),
        ManaCategory("Fuel", 2500, 5000, R.drawable.food_fork_drink),
        ManaCategory("Clothes", 800, 8000, R.drawable.food),
        ManaCategory("Other", 8000, 10000, R.drawable.food_fork_drink)
    )

    //todo get from database
    override fun getMana(): List<ManaCategory> {
        return defautManaCategories
    }
}
