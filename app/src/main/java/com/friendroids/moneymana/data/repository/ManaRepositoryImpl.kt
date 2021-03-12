package com.friendroids.moneymana.data.repository

import com.friendroids.moneymana.domain.repository.ManaRepository
import com.friendroids.moneymana.ui.presentation_models.ManaCategory

class ManaRepositoryImpl: ManaRepository {

    //temporary
    private val defautManaCategories = listOf(
        ManaCategory("Total budget", 87900, 100000),
        ManaCategory("Foods", 10000, 10000),
        ManaCategory("Fuel", 2500, 5000),
        ManaCategory("Clothes", 800, 8000),
        ManaCategory("Other", 8000, 10000)
    )

    //todo get from database
    override fun getManaCategories(): List<ManaCategory> {
        return defautManaCategories
    }
}
