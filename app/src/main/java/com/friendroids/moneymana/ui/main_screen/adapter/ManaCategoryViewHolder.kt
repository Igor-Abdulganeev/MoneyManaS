package com.friendroids.moneymana.ui.main_screen.adapter

import androidx.recyclerview.widget.RecyclerView
import com.friendroids.moneymana.R
import com.friendroids.moneymana.databinding.ItemManaBinding
import com.friendroids.moneymana.ui.presentation_models.ManaCategory

class ManaCategoryViewHolder(
    private val binding: ItemManaBinding,
    private val listener: (ManaCategory) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(manaCategory: ManaCategory) {
        with(binding) {
            itemManaNameTextView.text = manaCategory.title
            itemManaProgressBar.progress = manaCategory.percentRemained
            itemManaProgressBar.setIndicatorColor(manaCategory.status) //todo color resources
            itemManaProgressCustombar.setProgress(manaCategory.percentRemained)
            itemManaImageView.setImageResource(manaCategory.imageId)

            manaConstraintLayout.setOnClickListener {
                listener.invoke(manaCategory)
            }
        }
    }
}
