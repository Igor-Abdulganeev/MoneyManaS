package com.friendroids.moneymana.ui.mana_categories.adapter

import androidx.recyclerview.widget.RecyclerView
import com.friendroids.moneymana.databinding.ItemManaBinding
import com.friendroids.moneymana.ui.presentation_models.ManaCategory

class ManaCategoryViewHolder(
    private val binding: ItemManaBinding,
    private val listener: (ManaCategory) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(manaCategory: ManaCategory) {
        with(binding) {
            itemManaNameTextView.text = manaCategory.title
            itemManaCustomProgressBar.progress = manaCategory.percentRemained

            manaConstraintLayout.setOnClickListener {
                listener.invoke(manaCategory)
            }
        }
    }
}
