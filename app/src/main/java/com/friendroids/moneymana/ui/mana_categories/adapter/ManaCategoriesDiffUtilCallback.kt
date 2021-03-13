package com.friendroids.moneymana.ui.mana_categories.adapter

import androidx.recyclerview.widget.DiffUtil
import com.friendroids.moneymana.ui.presentation_models.ManaCategory

class ManaCategoriesDiffUtilCallback(
    private val oldList: List<ManaCategory>,
    private val newList: List<ManaCategory>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}