package com.ingresse.design.ui.button

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import com.ingresse.design.helper.ColorHelper
import com.ingresse.design.helper.ResourcesHelper
import com.ingresse.design.R
import java.lang.Exception

class DSButton(context: Context, attrs: AttributeSet): AppCompatButton(context, attrs, R.style.Button) {
    private val type: ButtonType
    private val size: ButtonSize
    private val style: ButtonStyle
    private val isThemed: Boolean

    private val resHelper = ResourcesHelper(context)
    private val colorHelper = ColorHelper(context)

    init {
        val array = context.theme.obtainStyledAttributes(attrs, R.styleable.DSButton, 0, 0)
        val sizeAttr = array.getInt(R.styleable.DSButton_size, 0)
        val styleAttr = array.getInt(R.styleable.DSButton_style, 0)
        val typeAttr = array.getInt(R.styleable.DSButton_companyTheme, 0)
        isThemed = array.getBoolean(R.styleable.DSButton_isThemed, false)

        type = ButtonType.fromId(typeAttr)
        size = ButtonSize.fromId(sizeAttr)
        style = ButtonStyle.fromId(styleAttr)

        setupSize()
        setupFont()
        if (isThemed) setupThemedBackground() else setupStyle()

        array.recycle()
    }

    private fun setupSize() {
        try { height = resHelper.resources.getDimensionPixelSize(size.height) }
        catch (ignored: Exception) { Log.w("DSButton", "Could not set height") }
    }

    private fun setupFont() {
        val states = arrayOf(
                intArrayOf(- android.R.attr.state_enabled),
                intArrayOf())
        val colors = intArrayOf(
                resHelper.getColorHelper(R.color.mercury),
                Color.WHITE)
        val textColors = ColorStateList(states, colors)

        setTextAppearance(context, R.style.TextStyle_Normal_Caps)
        setTextColor(textColors)
    }

    private fun setupStyle() {
        val colors = intArrayOf(
            resHelper.getColorHelper(R.color.mercury_crystal),
            resHelper.getColorHelper(style.pressed),
            resHelper.getColorHelper(style.normal))

        setBackgroundColors(colors)
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
                Color.parseColor(normalColor))

        setBackgroundColors(colors)
    }

    private fun setBackgroundColors(colors: IntArray) {
        val states = arrayOf(
            intArrayOf(- android.R.attr.state_enabled),
            intArrayOf(android.R.attr.state_pressed),
            intArrayOf())
        val bgColors = ColorStateList(states, colors)

        ViewCompat.setBackgroundTintList(this, bgColors)
        ViewCompat.setBackgroundTintMode(this, PorterDuff.Mode.SRC_ATOP)
        ViewCompat.setBackground(this, resHelper.getDrawableHelper(R.drawable.btn_solid_bg))
    }
}