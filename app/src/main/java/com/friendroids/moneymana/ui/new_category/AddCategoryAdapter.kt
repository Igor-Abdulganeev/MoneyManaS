package com.friendroids.moneymana.ui.new_category

/*
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.friendroids.moneymana.R

class AddCategoryAdapter(private var imageCategories: List<Int>) :
    RecyclerView.Adapter<AddCategoryViewHolder>() {

    private var _selected_position = 0
    val selected_position get() = _selected_position

    private var _idSave = R.drawable.adjust
    val idSave get() = _idSave

    private fun getItem(position: Int): Int = imageCategories[position]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddCategoryViewHolder {
        return AddCategoryViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        )
    }

    override fun onBindViewHolder(holder: AddCategoryViewHolder, position: Int) {
        holder.bind(getItem(position))

        if (selected_position == position)
            holder.itemView.setBackgroundColor(Color.DKGRAY)
        else
            holder.itemView.setBackgroundColor(Color.TRANSPARENT)

        holder.itemView.setOnClickListener {
            if (position != RecyclerView.NO_POSITION) {
                notifyItemChanged(_selected_position)
                _selected_position = position
                notifyItemChanged(_selected_position)
                _idSave = getItem(position)
            }
        }

    }

    override fun getItemCount(): Int = imageCategories.size
}

class AddCategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val dateAndSumma: ImageView = itemView.findViewById(R.id.item_select_image_view)
    private val selectTextView: TextView = itemView.findViewById(R.id.item_select_text_view)

    fun bind(id: Int) {
        dateAndSumma.setImageResource(id)
        selectTextView.text = id.toString()
    }
}*/
