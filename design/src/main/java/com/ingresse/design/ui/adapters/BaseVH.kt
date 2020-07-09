package com.ingresse.design.ui.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseVH<T>(private val view: View): RecyclerView.ViewHolder(view) {
    open fun bind(item: T) {}
}