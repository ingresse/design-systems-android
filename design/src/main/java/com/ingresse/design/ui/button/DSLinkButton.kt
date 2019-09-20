package com.ingresse.design.ui.button

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import com.ingresse.design.R
import com.ingresse.design.helper.ColorHelper
import com.ingresse.design.helper.ResourcesHelper

class DSLinkButton(context: Context, attrs: AttributeSet): AppCompatButton(context, attrs) {
    private val resHelper = ResourcesHelper(context)
    private val colorHelper = ColorHelper(context)

    init {
        val darkColor = colorHelper.primaryDarkColor
        val normalColor = colorHelper.primaryColor

        val colors = intArrayOf(
                resHelper.getColorHelper(R.color.mercury_crystal),
                Color.parseColor(darkColor),
                Color.parseColor(normalColor))

        val states = arrayOf(
                intArrayOf(- android.R.attr.state_enabled),
                intArrayOf(android.R.attr.state_pressed),
                intArrayOf())

        val bgColors = ColorStateList(states, colors)

        setTextColor(bgColors)
        setBackgroundColor(resHelper.getColorHelper(R.color.transparent))
    }
}