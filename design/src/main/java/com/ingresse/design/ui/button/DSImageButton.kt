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
    private val theme: ButtonTheme
    private val type: ButtonType

    private val isThemed: Boolean

    private val resHelper = ResourcesHelper(context)
    private val colorHelper = ColorHelper(context)

    init {
        val array = context.theme.obtainStyledAttributes(attrs, R.styleable.DSImageButton, 0, 0)
        val themeAttr = array.getInt(R.styleable.DSImageButton_buttonTheme, 0)
        val typeAttr = array.getInt(R.styleable.DSImageButton_type, 0)
        isThemed = array.getBoolean(R.styleable.DSImageButton_isThemed, false)

        theme = ButtonTheme.fromId(themeAttr)
        type = ButtonType.fromId(typeAttr)

        setupImage()
        if (isThemed) setupThemedBackground() else setupStyle()

        array.recycle()
    }

    private fun setupImage() {
        setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
    }

    private fun setupThemedBackground() {
        val darkColor = when (theme) {
            ButtonTheme.PRIMARY -> colorHelper.primaryDarkColor
            ButtonTheme.ACCENT -> colorHelper.secondaryDarkColor
            ButtonTheme.CONFIRM -> colorHelper.confirmDarkColor
        }

        val normalColor = when (theme) {
            ButtonTheme.PRIMARY -> colorHelper.primaryColor
            ButtonTheme.ACCENT -> colorHelper.secondaryColor
            ButtonTheme.CONFIRM -> colorHelper.confirmColor
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
            resHelper.getColorHelper(type.pressed),
            resHelper.getColorHelper(type.normal)
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
        ViewCompat.setBackgroundTintMode(this, PorterDuff.Mode.MULTIPLY)
        ViewCompat.setBackground(this, resHelper.getDrawableHelper(R.drawable.btn_solid_bg))
    }
}