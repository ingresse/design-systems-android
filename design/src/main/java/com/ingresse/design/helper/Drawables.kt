package com.ingresse.design.helper

import android.content.Context
import android.graphics.drawable.GradientDrawable
import androidx.annotation.ColorRes

fun GradientDrawable.setColor(context: Context,
                              @ColorRes colorRes: Int) {
    val resHelper = ResourcesHelper(context)
    val color = resHelper.getColorHelper(colorRes)
    setColor(color)
}

fun GradientDrawable.setColors(context: Context,
                               @ColorRes topColorRes: Int,
                               @ColorRes bottomColorRes: Int) {
    val resHelper = ResourcesHelper(context)
    val topColor = resHelper.getColorHelper(topColorRes)
    val bottomColor = resHelper.getColorHelper(bottomColorRes)
    animateGradient(this, bottomColor, topColor)
}