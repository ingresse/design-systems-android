package com.ingresse.design.ui.button

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.view.ViewCompat
import com.ingresse.design.R
import com.ingresse.design.helper.ColorHelper
import com.ingresse.design.helper.ResourcesHelper

class DSImageButton(context: Context, attrs: AttributeSet) : AppCompatImageButton(context, attrs) {
    private val type: ButtonType
    private val style: ButtonStyle

    private val isThemed: Boolean

    private val resHelper = ResourcesHelper(context)
    private val colorHelper = ColorHelper(context)

    init {
        val array = context.theme.obtainStyledAttributes(attrs, R.styleable.ThemedImageButton, 0, 0)
        val typeAttr = array.getInt(R.styleable.ThemedImageButton_companyTheme, 0)
        val styleAttr = array.getInt(R.styleable.ThemedImageButton_style, 0)
        isThemed = array.getBoolean(R.styleable.ThemedImageButton_isThemed, false)

        type = ButtonType.fromId(typeAttr)
        style = ButtonStyle.fromId(styleAttr)

        setupImage()
        if (isThemed) setupThemedBackground() else setupStyle()

        array.recycle()
    }

    private fun setupImage() {
        setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
    }

    private fun setupThemedBackground() {
        val darkColor = when (type) {
            ButtonType.PRIMARY -> colorHelper.primaryDarkColor
            ButtonType.ACCENT -> colorHelper.secondaryDarkColor
        }

        val normalColor = when (type) {
            ButtonType.PRIMARY -> colorHelper.primaryColor
            ButtonType.ACCENT -> colorHelper.secondaryColor
        }

        val colors = intArrayOf(
            resHelper.getColorHelper(R.color.mercury_crystal),
            Color.parseColor(darkColor),
            Color.parseColor(normalColor)
        )

        setBackgroundColors(colors)
    }

    private fun setupStyle() {
        val colors = intArrayOf(
            resHelper.getColorHelper(R.color.mercury_crystal),
            resHelper.getColorHelper(style.pressed),
            resHelper.getColorHelper(style.normal)
        )

        setBackgroundColors(colors)
    }

    private fun setBackgroundColors(colors: IntArray) {
        val states = arrayOf(
            intArrayOf(-android.R.attr.state_enabled),
            intArrayOf(android.R.attr.state_pressed),
            intArrayOf()
        )
        val bgColors = ColorStateList(states, colors)

        ViewCompat.setBackgroundTintList(this, bgColors)
        ViewCompat.setBackgroundTintMode(this, PorterDuff.Mode.SRC_ATOP)
        ViewCompat.setBackground(this, resHelper.getDrawableHelper(R.drawable.btn_solid_bg))
    }
}