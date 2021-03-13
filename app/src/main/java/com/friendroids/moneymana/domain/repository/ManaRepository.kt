package com.friendroids.moneymana.domain.repository

import com.friendroids.moneymana.ui.presentation_models.ManaCategory

interface ManaRepository {

    fun getManaCategories(): List<ManaCategory>
}
