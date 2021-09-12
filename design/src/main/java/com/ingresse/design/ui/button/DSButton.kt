package com.ingresse.design.ui.button

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.LayerDrawable
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import com.ingresse.design.R
import com.ingresse.design.helper.ColorHelper
import com.ingresse.design.helper.ResourcesHelper

class DSButton(context: Context, attrs: AttributeSet): AppCompatButton(context, attrs, R.style.Button) {
    private val type: ButtonTheme
    private val size: ButtonSize
    private var style: ButtonType
    private val isThemed: Boolean
    private val isLink: Boolean
    private val isTextAllCaps: Boolean
    private var isBordered: Boolean

    private val resHelper = ResourcesHelper(context)
    private val colorHelper = ColorHelper(context)

    init {
        val array = context.theme.obtainStyledAttributes(attrs, R.styleable.DSButton, 0, 0)
        val sizeAttr = array.getInt(R.styleable.DSButton_size, 0)
        val styleAttr = array.getInt(R.styleable.DSButton_ds_type, 0)
        val themeAttr = array.getInt(R.styleable.DSButton_buttonTheme, 0)
        val alignment = array.getInt(R.styleable.DSButton_alignment, Gravity.CENTER)
        isThemed = array.getBoolean(R.styleable.DSButton_isThemed, false)
        isLink = array.getBoolean(R.styleable.DSButton_isLink, false)
        isTextAllCaps = array.getBoolean(R.styleable.DSButton_isTextAllCaps, false)
        isBordered = array.getBoolean(R.styleable.DSButton_isBordered, false)

        type = ButtonTheme.fromId(themeAttr)
        size = ButtonSize.fromId(sizeAttr)
        style = ButtonType.fromId(styleAttr)
        gravity = alignment

        setupSize()
        setupFont()

        when {
            isBordered -> setBorderedButton()
            isThemed -> setupThemedBackground()
            else -> setupStyle()
        }

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
                if (!isLink) Color.WHITE else resHelper.getColorHelper(R.color.ocean))
        val textColors = ColorStateList(states, colors)

        val style = if (isTextAllCaps) R.style.TextStyle_Normal_Caps else R.style.TextStyle_Normal_No_Caps
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) setTextAppearance(style)
        else setTextAppearance(context, style)

        setTextColor(textColors)
    }

    private fun setupStyle() {
        val colors = intArrayOf(
            resHelper.getColorHelper(R.color.mercury_crystal),
            resHelper.getColorHelper(style.pressed),
            resHelper.getColorHelper(style.normal))

        setBackgroundColors(colors)
    }

    private fun setBorderedButton() {
        val color = resHelper.getColorHelper(style.normal)
        setBackgroundResource(R.drawable.ds_stroked_button_bg)
        setTextColor(color)
        (background as LayerDrawable).apply {
            getDrawable(0).setColorFilter(Color.TRANSPARENT, PorterDuff.Mode.SRC_IN)
            getDrawable(1).setColorFilter(color, PorterDuff.Mode.SRC_IN)
        }
    }

    private fun setupThemedBackground() {
        val darkColor = when (type) {
            ButtonTheme.PRIMARY -> colorHelper.primaryDarkColor
            ButtonTheme.ACCENT -> colorHelper.secondaryDarkColor
            ButtonTheme.CONFIRM -> colorHelper.confirmDarkColor
        }

        val normalColor = when (type) {
            ButtonTheme.PRIMARY -> colorHelper.primaryColor
            ButtonTheme.ACCENT -> colorHelper.secondaryColor
            ButtonTheme.CONFIRM -> colorHelper.confirmColor
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

    fun setStyle(buttonType: ButtonType) {
        style = buttonType
        setupStyle()
    }

    fun setBordered() {
        isBordered = true
        setBorderedButton()
    }
}
