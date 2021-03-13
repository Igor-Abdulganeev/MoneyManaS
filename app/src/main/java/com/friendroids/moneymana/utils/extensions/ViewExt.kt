package com.friendroids.moneymana.utils.extensions

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat

fun View.changeColor(context: Context, color: Int) {
    setBackgroundColor(
        ContextCompat.getColor(
            context,
            color
        )
    )
}