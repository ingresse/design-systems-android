package com.ingresse.design.ui.button

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.TextView
import com.ingresse.design.R
import com.ingresse.design.helper.ColorHelper
import com.ingresse.design.helper.ResourcesHelper
import com.ingresse.design.helper.setInvisible
import kotlinx.android.synthetic.main.custom_button_item.view.*

class DSButtonItem(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {
    private val item: TextView get() = txt_item

    init {
        inflate(context, R.layout.custom_button_item, this)

        val primaryColor = ColorHelper(context).primaryColor
        val iconColor = Color.parseColor(primaryColor)
        val defaultTextColor = ResourcesHelper(context).getColorHelper(R.color.oil)
        val array = context.theme.obtainStyledAttributes(attrs, R.styleable.DSButtonItem, 0, 0)
        val withEndImage = array.getBoolean(R.styleable.DSButtonItem_withEndIcon, true)
        val text = array.getString(R.styleable.DSButtonItem_text).orEmpty()
        val textColor = array.getColor(R.styleable.DSButtonItem_textColor, defaultTextColor)

        img_end.setInvisible(!withEndImage)
        img_end.setColorFilter(iconColor, PorterDuff.Mode.SRC_ATOP)
        item.text = text
        item.setTextColor(textColor)
        array.recycle()
    }
}