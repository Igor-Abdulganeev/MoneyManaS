package com.friendroids.moneymana.ui.fragment_category.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.friendroids.moneymana.db.models.CheckEntity

class ManaCategoryAdapter :
    ListAdapter<CheckEntity, ManaCategoryViewHolder>(ManaCategoryDiffUtils()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ManaCategoryViewHolder {
        return ManaCategoryViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ManaCategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
