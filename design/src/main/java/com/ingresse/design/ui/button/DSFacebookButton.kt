package com.ingresse.design.ui.button

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import com.ingresse.design.R
import com.ingresse.design.helper.ResourcesHelper
import kotlinx.android.synthetic.main.custom_facebook_button.view.*

class DSFacebookButton(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {
    private val resHelper = ResourcesHelper(context)
    private val mainLayout: ConstraintLayout get() = layout_facebook_button

    init {
        View.inflate(context, R.layout.custom_facebook_button, this)
        setBackgroundColor()
    }

    private fun setBackgroundColor() {
        val states = arrayOf(
                intArrayOf(-android.R.attr.state_enabled),
                intArrayOf(android.R.attr.state_pressed),
                intArrayOf())

        val colors = intArrayOf(
                resHelper.getColorHelper(R.color.mercury_crystal),
                resHelper.getColorHelper(R.color.mariner_dark),
                resHelper.getColorHelper(R.color.mariner))

        val bgColors = ColorStateList(states, colors)

        ViewCompat.setBackgroundTintList(mainLayout, bgColors)
        ViewCompat.setBackgroundTintMode(mainLayout, PorterDuff.Mode.MULTIPLY)
        ViewCompat.setBackground(mainLayout, resHelper.getDrawableHelper(R.drawable.btn_solid_bg))
    }
}