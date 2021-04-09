package com.friendroids.moneymana.ui.fragment_category.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.friendroids.moneymana.databinding.ItemCheckBinding
import com.friendroids.moneymana.db.models.CheckEntity
import com.friendroids.moneymana.utils.extensions.DateTimeConverter

class ManaCategoryViewHolder(private val binding: ItemCheckBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        check: CheckEntity
    ) {
        with(binding) {
            checkDatetimeTextview.text =
                DateTimeConverter().setDateTimeStringFromLong(check.dateCheck)
            checkSumTextview.text = check.sumCheck.toString()
        }
    }

    companion object {
        fun from(parent: ViewGroup): ManaCategoryViewHolder {
            return ManaCategoryViewHolder(
                binding = ItemCheckBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }
}