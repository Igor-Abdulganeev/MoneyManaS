package com.friendroids.moneymana.ui.mana_categories.adapter

import androidx.core.content.ContextCompat
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
            itemManaImageView.setImageResource(manaCategory.imageId)
            itemManaCustomProgressBar.progress = manaCategory.percentRemained
            itemManaSumLimitTextView.text = manaCategory.maxSum.toString()
            if (manaCategory.sumRemained >= 0) {
                itemManaSumRemainedTextView.text =
                    itemView.context.getString(R.string.sum_remained, manaCategory.sumRemained)
            } else {
                itemManaSumRemainedTextView.text =
                    itemView.context.getString(R.string.overrun_sum, manaCategory.sumRemained)
            }
//            itemManaSumRemainedTextView.setTextColor(
//                ContextCompat.getColor(itemView.context, manaCategory.status)
//            )

            manaConstraintLayout.setOnClickListener {
                listener.invoke(manaCategory)
            }
        }
    }
}
