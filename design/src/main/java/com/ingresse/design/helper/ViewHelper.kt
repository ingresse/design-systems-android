package com.ingresse.design.helper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun View.setVisible(condition: Boolean = true) {
    visibility = if (condition) View.VISIBLE else View.GONE
}

fun View.setInvisible(condition: Boolean = true) {
    visibility = if (condition) View.INVISIBLE else View.VISIBLE
}

fun View.setViewEnabled(condition: Boolean = true) {
    isEnabled = condition
    alpha = if (condition) 1.0f else 0.6f
}