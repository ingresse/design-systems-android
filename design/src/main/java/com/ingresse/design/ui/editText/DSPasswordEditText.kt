package com.ingresse.design.ui.editText

import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.ColorRes
import androidx.annotation.RequiresApi
import com.ingresse.design.R
import com.ingresse.design.helper.ResourcesHelper
import kotlinx.android.synthetic.main.ds_password_edit_text.view.*

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class DSPasswordEditText(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {
    private val resHelper = ResourcesHelper(context)
    private val halfLevel: Drawable
    private val fullLevel: Drawable

    var strength = Strength.NONE
    fun isWrong(): Boolean = editText.isWrong
            && (strength != Strength.STRONG || strength != Strength.MEDIUM)

    val editText: DSEditText get() = edt_password

    init {
        View.inflate(context, R.layout.ds_password_edit_text, this)

        halfLevel = resHelper.getDrawableHelper(R.drawable.progress_level_half)
        fullLevel = resHelper.getDrawableHelper(R.drawable.progress_level_full)
        setEmptyValidation()

        editText.invalidate()
        editText.config(attrs)
    }

    fun setWeakPassword() = setProgressValues(halfLevel, Strength.WEAK, 1)
    fun setMediumPassword() = setProgressValues(halfLevel, Strength.MEDIUM, 2)
    fun setStrongPassword() = setProgressValues(fullLevel, Strength.STRONG, 3)
    fun setEmptyValidation() = setProgressValues(fullLevel, Strength.NONE, 0)

    private fun setProgressValues(level: Drawable, strength: Strength, progress: Int) {
        setProgress(progress)
        progress_strength_password.progressDrawable = level
        progress_strength_password.progressTintList = getProgressColor(strength)
        this.strength = strength
    }

    private fun setProgress(value: Int)
        = ObjectAnimator.ofInt(progress_strength_password, "progress", value)
            .setDuration(300)
            .start()

    private fun getProgressColor(color: Strength): ColorStateList {
        val colors = intArrayOf(resHelper.getColorHelper(color.value))
        val states = arrayOf(intArrayOf())
        return ColorStateList(states, colors)
    }

    enum class Strength(@ColorRes val value: Int) {
        WEAK(R.color.ruby),
        MEDIUM(R.color.tangerine),
        STRONG(R.color.bamboo),
        NONE(R.color.transparent)
    }

    // OVERRIDE METHODS TO EASY ACCESS
    fun getTextDS() = editText.getTextDS()
    fun setWatcher(onTextChange: (text: String) -> Unit) = editText.setWatcher(onTextChange)
    fun setActionListener(listener: () -> Unit) = editText.setActionListener(listener)
}