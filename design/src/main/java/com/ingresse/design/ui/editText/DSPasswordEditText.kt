package com.ingresse.design.ui.editText

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.ColorRes
import com.ingresse.design.R
import com.ingresse.design.helper.ResourcesHelper
import kotlinx.android.synthetic.main.ds_password_edit_text.view.*

class DSPasswordEditText(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {
    private val resHelper = ResourcesHelper(context)
    private val halfLevel: Drawable
    private val fullLevel: Drawable

    var strength = Strength.NONE
    fun isWrong() = strength == Strength.NONE || strength == Strength.WEAK || getTextCount() < 7

    val editText: DSEditText get() = edt_password

    init {
        View.inflate(context, R.layout.ds_password_edit_text, this)

        halfLevel = resHelper.getDrawableHelper(R.drawable.progress_level_half)
        fullLevel = resHelper.getDrawableHelper(R.drawable.progress_level_full)
        setEmptyValidation()

        editText.config(attrs)
        setFocusListener()
    }

    private fun setEmptyValidation() = setProgressValues(fullLevel, Strength.NONE, 0)

    private fun setProgressValues(level: Drawable, strength: Strength, progress: Int) {
        progress_strength_password.progressDrawable = level

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            progress_strength_password.setProgress(progress, true)
        } else {
            progress_strength_password.progress = progress
        }

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            progress_strength_password.progressTintList = getProgressColorList(strength)
        } else {
            progress_strength_password.progressDrawable.mutate().setColorFilter(getProgressColor(strength), PorterDuff.Mode.SRC_IN)
        }

        this.strength = strength
    }

    private fun getProgressColorList(color: Strength): ColorStateList {
        val colors = intArrayOf(resHelper.getColorHelper(color.value))
        val states = arrayOf(intArrayOf())
        return ColorStateList(states, colors)
    }

    private fun getProgressColor(color: Strength) = resHelper.getColorHelper(color.value)

    enum class Strength(@ColorRes val value: Int) {
        WEAK(R.color.ruby),
        MEDIUM(R.color.tangerine),
        STRONG(R.color.bamboo),
        NONE(R.color.transparent)
    }

    /**
     * Open fun: change progress color by password strength
     */
    fun setPasswordStrength(progress: Int) {
        when (progress) {
            1 -> setProgressValues(halfLevel, Strength.WEAK, 1)
            2 -> setProgressValues(halfLevel, Strength.MEDIUM, 2)
            3 -> setProgressValues(fullLevel, Strength.STRONG, 3)
            else -> setEmptyValidation()
        }
    }

    /**
     * Custom focus listener:
     * Need to use attr customValidation = "true"
     */
    private fun setFocusListener() {
        editText.setFocusChangeListener focus@{ hasFocus ->
            if (hasFocus) return@focus editText.setEditTextDefault(hasFocus)
            if (isWrong()) return@focus editText.setEditTextError()
            editText.setEditTextDefault()
        }
    }

    // OVERRIDE METHODS TO EASY ACCESS
    fun getTextDS() = editText.getTextDS()
    fun getTextCount() = editText.text.count()
    fun setWatcher(onTextChange: (text: String) -> Unit) = editText.setWatcher(onTextChange)
    fun setActionListener(listener: () -> Unit) = editText.setActionListener(listener)
}