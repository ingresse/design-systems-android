package com.ingresse.design.ui.button

import android.content.Context
import android.content.res.ColorStateList
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.drawable.DrawableCompat
import com.ingresse.design.R
import com.ingresse.design.helper.ResourcesHelper
import kotlinx.android.synthetic.main.custom_facebook_button.view.*

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
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

        val drawable = DrawableCompat.wrap(resHelper.getDrawableHelper(R.drawable.btn_solid_bg))
        drawable.setTintList(bgColors)
        mainLayout.background = drawable
    }
}