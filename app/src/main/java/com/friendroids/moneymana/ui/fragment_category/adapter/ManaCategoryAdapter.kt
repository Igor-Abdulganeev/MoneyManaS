package com.friendroids.moneymana.ui.fragment_category.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.friendroids.moneymana.R
import com.friendroids.moneymana.db.models.CheckEntity

class ManaCategoryAdapter(private var checksEntity: List<CheckEntity>) :
    RecyclerView.Adapter<CheckViewHolder>() {

    private fun getItem(position: Int): CheckEntity = checksEntity[position]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckViewHolder {
        return CheckViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_check, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CheckViewHolder, position: Int) =
        holder.bind(getItem(position))

    override fun getItemCount(): Int = checksEntity.size
}

class CheckViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val dateAndSumma: TextView = itemView.findViewById(R.id.item_check_text_view)

    fun bind(checkEntity: CheckEntity) {
        dateAndSumma.text = "${checkEntity.dateCheck} - ${checkEntity.sumCheck}"
    }

}