package com.ingresse.design.ui.button

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import com.ingresse.design.helper.ColorHelper
import com.ingresse.design.helper.ResourcesHelper
import com.ingresse.design.R
import java.lang.Exception

class DSButton(context: Context, attrs: AttributeSet): AppCompatButton(context, attrs, R.style.Button) {
    private val type: ButtonTheme
    private val size: ButtonSize
    private val style: ButtonType
    private val isThemed: Boolean

    private val resHelper = ResourcesHelper(context)
    private val colorHelper = ColorHelper(context)

    init {
        val array = context.theme.obtainStyledAttributes(attrs, R.styleable.DSButton, 0, 0)
        val sizeAttr = array.getInt(R.styleable.DSButton_size, 0)
        val styleAttr = array.getInt(R.styleable.DSButton_type, 0)
        val themeAttr = array.getInt(R.styleable.DSButton_buttonTheme, 0)
        val alignment = array.getInt(R.styleable.DSButton_alignment, Gravity.CENTER)
        isThemed = array.getBoolean(R.styleable.DSButton_isThemed, false)

        type = ButtonTheme.fromId(themeAttr)
        size = ButtonSize.fromId(sizeAttr)
        style = ButtonType.fromId(styleAttr)
        gravity = alignment

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) setTextAppearance(R.style.TextStyle_Normal_Caps)
        else setTextAppearance(context, R.style.TextStyle_Normal_Caps)

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
            ButtonTheme.PRIMARY -> colorHelper.primaryDarkColor
            ButtonTheme.ACCENT -> colorHelper.secondaryDarkColor
        }

        val normalColor = when (type) {
            ButtonTheme.PRIMARY -> colorHelper.primaryColor
            ButtonTheme.ACCENT -> colorHelper.secondaryColor
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
        ViewCompat.setBackgroundTintMode(this, PorterDuff.Mode.MULTIPLY)
        ViewCompat.setBackground(this, resHelper.getDrawableHelper(R.drawable.btn_solid_bg))
    }
}