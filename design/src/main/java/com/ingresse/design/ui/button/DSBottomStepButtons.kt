package com.ingresse.design.ui.button

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.ingresse.design.R
import com.ingresse.design.helper.ResourcesHelper
import kotlinx.android.synthetic.main.ds_bottom_step_buttons.view.*

class DSBottomStepButtons(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {
    private val resHelper = ResourcesHelper(context)

    init { inflate(context, R.layout.ds_bottom_step_buttons, this) }

    fun setOnLeftClick(onClick: ((View) -> Unit)) { btn_left.setOnClickListener(onClick) }

    fun setOnRightClick(onClick: ((View) -> Unit)) { btn_right.setOnClickListener(onClick) }

    fun setLeftButtonEnabled(enabled: Boolean) {
        val tintColor = tintColor(enabled)
        img_left_button.setColorFilter(tintColor, android.graphics.PorterDuff.Mode.SRC_IN)
        btn_left.isEnabled = enabled
        lbl_left_button.isEnabled = enabled
    }

    fun setRightButtonEnabled(enabled: Boolean = true) {
        val tintColor = tintColor(enabled)
        img_right_button.setColorFilter(tintColor, android.graphics.PorterDuff.Mode.SRC_IN)
        btn_right.isEnabled = enabled
        lbl_right_button.isEnabled = enabled
    }

    private fun tintColor(enabled: Boolean): Int {
        val colorRes = if (enabled) R.color.ocean else R.color.mercury_30
        return resHelper.getColorHelper(colorRes)
    }
}