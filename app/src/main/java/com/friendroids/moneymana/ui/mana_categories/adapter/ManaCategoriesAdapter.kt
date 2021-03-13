package com.friendroids.moneymana.ui.mana_categories.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.friendroids.moneymana.databinding.ItemManaBinding
import com.friendroids.moneymana.ui.presentation_models.ManaCategory

class ManaCategoriesAdapter(private val listener: (ManaCategory) -> Unit) : RecyclerView.Adapter<ManaCategoryViewHolder>() {

    private var items = listOf<ManaCategory>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ManaCategoryViewHolder {
        return ManaCategoryViewHolder(
            ItemManaBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            listener
        )
    }

    override fun onBindViewHolder(holder: ManaCategoryViewHolder, position: Int) {
        holder.onBind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun bindItems(newItems: List<ManaCategory>) {
        items = newItems
    }
}
