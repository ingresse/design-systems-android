package com.ingresse.design.ui.editText

import android.content.Context
import android.graphics.drawable.DrawableContainer.DrawableContainerState
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.StateListDrawable
import android.util.AttributeSet
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.AppCompatEditText
import com.ingresse.design.R
import com.ingresse.design.helper.ResourcesHelper

class DSBorderedEditText(context: Context, private val attributes: AttributeSet)
    : AppCompatEditText(ContextThemeWrapper(context, R.style.DSTextStyle_Regular_B1), attributes) {
    private var resHelper: ResourcesHelper = ResourcesHelper(context)
    private var strokeWidth = context.resources.getDimensionPixelSize(R.dimen.spacing_x0_25)

    var isWrong = false
    set(value) {
        field = value
        updateView()
    }

    init {
        setHintTextColor(resHelper.getColorHelper(R.color.mercury_10))
        background = resHelper.getDrawableHelper(R.drawable.ds_bordered_edit_text_bg)

        val spacing2 = context.resources.getDimensionPixelSize(R.dimen.spacing_x2)
        val spacing3 = context.resources.getDimensionPixelSize(R.dimen.spacing_x3)
        setPadding(spacing3, spacing2, spacing3, spacing2)

        val warningIcon = resHelper.getDrawableHelper(R.drawable.ic_warning)
        setCompoundDrawablesWithIntrinsicBounds(null, null, warningIcon, null)
        setTextColor(resHelper.getColorHelper(R.color.mercury_70))

        val array = context.theme.obtainStyledAttributes(attributes, R.styleable.error, 0, 0)
        isWrong = array.getBoolean(R.styleable.error_isWrong, false)
        updateView()
    }

    private fun updateView() {
        val backgroundResColor = if (isWrong) R.color.ruby else R.color.ocean
        val backgroundColor = resHelper.getColorHelper(backgroundResColor)

        val rightImage = if (isWrong) resHelper.getDrawableHelper(R.drawable.ic_warning) else null
        setCompoundDrawablesWithIntrinsicBounds(null, null, rightImage, null)

        (background as StateListDrawable).apply {

            val drawableContainerState = constantState as DrawableContainerState
            val children = drawableContainerState.children

            val disableState = children[0] as GradientDrawable
            val enableState = children[1] as GradientDrawable
            val focusedState = children[2] as GradientDrawable

            val notFocusedWidth = if (isWrong) strokeWidth else 0

            disableState.setStroke(notFocusedWidth, backgroundColor)
            enableState.setStroke(notFocusedWidth, backgroundColor)
            focusedState.setStroke(strokeWidth, backgroundColor)
        }
    }
}