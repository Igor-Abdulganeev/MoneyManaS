package com.friendroids.moneymana.ui.fragment_category.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.friendroids.moneymana.db.models.CheckEntity

class ManaCategoryDiffUtils : DiffUtil.ItemCallback<CheckEntity>() {
    override fun areItemsTheSame(oldItem: CheckEntity, newItem: CheckEntity): Boolean {
        return oldItem.id == newItem.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: CheckEntity, newItem: CheckEntity): Boolean {
        return oldItem == newItem
    }
}