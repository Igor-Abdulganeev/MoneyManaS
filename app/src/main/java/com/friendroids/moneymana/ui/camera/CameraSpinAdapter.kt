package com.friendroids.moneymana.ui.camera

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.friendroids.moneymana.R
import com.friendroids.moneymana.ui.presentation_models.ManaCategory

class CameraSpinAdapter(
        private val contextMain: Context,
        private val arrayCategory: List<ManaCategory>
) :
        ArrayAdapter<ManaCategory>(contextMain, R.layout.item_category, arrayCategory) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getItemsView(position, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getItemsView(position, parent)
    }

    private fun getItemsView(position: Int, parent: ViewGroup): View {
        val inflater = LayoutInflater.from(contextMain)
        val row: View = inflater.inflate(R.layout.item_category, parent, false)
        val nameCategory = row.findViewById<TextView>(R.id.category_name_text)
        val imageCategory = row.findViewById<ImageView>(R.id.category_image)

        imageCategory.setImageResource(arrayCategory[position].imageId)
        nameCategory.text = arrayCategory[position].title

        return row
    }
}