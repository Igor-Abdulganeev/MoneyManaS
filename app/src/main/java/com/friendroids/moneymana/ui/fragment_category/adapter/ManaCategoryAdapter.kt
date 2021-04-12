package com.friendroids.moneymana.ui.fragment_category.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.friendroids.moneymana.R
import com.friendroids.moneymana.db.models.CheckEntity

class ManaCategoryAdapter(private var checksEntity: List<CheckEntity>, private val clickListener: ActionChecks) :
    RecyclerView.Adapter<CheckViewHolder>() {

    private fun getItem(position: Int): CheckEntity = checksEntity[position]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckViewHolder {
        return CheckViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_check, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CheckViewHolder, position: Int) =
        holder.bind(getItem(position), clickListener)

    override fun getItemCount(): Int = checksEntity.size
}

class CheckViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val dateAndSum: TextView = itemView.findViewById(R.id.item_check_text_view)
    private val imageRemove: ImageView = itemView.findViewById(R.id.item_check_image_remove)

    fun bind(checkEntity: CheckEntity, clickListener: ActionChecks) {
        dateAndSum.text = "${checkEntity.dateCheck} - ${checkEntity.sumCheck}"
        imageRemove.setOnClickListener {
            clickListener.onRemove(checkEntity.id)
        }
    }
}

interface ActionChecks {
    fun onRemove(id: Long?)
}