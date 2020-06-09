package com.ingresse.design.ui.button

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import com.ingresse.design.R
import com.ingresse.design.helper.ResourcesHelper
import kotlinx.android.synthetic.main.ds_two_label_button.view.*

class DSTwoLabelButton(context: Context, attrs: AttributeSet): ConstraintLayout(context, attrs) {
    val resHelper = ResourcesHelper(context)
    var type: ButtonType = ButtonType.PRIMARY
        set(value) {
            field = value
            setupButtonType()
        }

    var leftTitle = ""
        set(value) {
            field = value
            lbl_left.text = value
        }

    var rightTitle = ""
        set(value) {
            field = value
            lbl_right.text = value
        }

    init {
        inflate(context, R.layout.ds_two_label_button, this)
        val array = context.theme.obtainStyledAttributes(attrs, R.styleable.DSTwoLabelButton, 0, 0)
        val dsTypeAttr = array.getInt(R.styleable.DSTwoLabelButton_ds_type, 0)
        leftTitle = array.getString(R.styleable.DSTwoLabelButton_leftTitle).orEmpty()
        rightTitle = array.getString(R.styleable.DSTwoLabelButton_rightTitle).orEmpty()
        isEnabled = array.getBoolean(R.styleable.DSTwoLabelButton_isEnabled, true)
        layout_btn.isEnabled = isEnabled
        type = ButtonType.fromId(dsTypeAttr)
    }

    /**
     * Function to set button properties
     *
     * @param leftTitle - Text on button start
     * @param rightTitle - Text on button end
     * @param type - Variable to set button type and colors
     */
    fun setButtonProperties(leftTitle: String,
                            rightTitle: String,
                            type: ButtonType) {
        this.leftTitle = leftTitle
        this.rightTitle = rightTitle
        this.type = type
        setupButtonType()
        setTextColor()
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        layout_btn.isEnabled = enabled
    }

    private fun setTextColor() {
        val textColor = if (isEnabled) R.color.white else R.color.mercury_light
        val color = resHelper.getColorHelper(textColor)
        lbl_left.setTextColor(color)
        lbl_right.setTextColor(color)
    }

    private fun setupButtonType() {
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

        ViewCompat.setBackgroundTintList(layout_btn, bgColors)
        ViewCompat.setBackgroundTintMode(layout_btn, PorterDuff.Mode.SRC_IN)
    }
}