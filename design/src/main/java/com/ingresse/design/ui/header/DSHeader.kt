package com.ingresse.design.ui.header

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.view.View.OnClickListener
import android.widget.FrameLayout
import androidx.core.view.isVisible
import com.ingresse.design.R
import com.ingresse.design.helper.ColorHelper
import kotlinx.android.synthetic.main.custom_header.view.*

class DSHeader(context: Context, attrs: AttributeSet): FrameLayout(context, attrs) {
    init {
        inflate(context, R.layout.custom_header, this)

        val array = context.theme.obtainStyledAttributes(attrs, R.styleable.DSHeader, 0, 0)
        val title = array.getString(R.styleable.DSHeader_title).orEmpty()
        txt_screen_title.text = title
        
        val description = array.getString(R.styleable.DSHeader_description)
        if (description.isNullOrEmpty()) txt_screen_description.visibility = View.GONE
        else txt_screen_description.text = description

        when (array.getInt(R.styleable.DSHeader_headerType, 0)) {
            0 -> setSimpleHeader()
            1 -> setReturnType()
            2 -> setCloseType()
        }

        val primaryColor = ColorHelper(context).primaryColor
        val iconColor = Color.parseColor(primaryColor)
        btn_back.setColorFilter(iconColor)
        btn_close.setColorFilter(iconColor)
    }
    
    private fun setSimpleHeader() {
        btn_close.visibility = View.GONE
        btn_back.visibility = View.GONE
    }
    
    private fun setReturnType() {
        btn_close.visibility = View.GONE
        btn_back.visibility = View.VISIBLE
    }
    
    private fun setCloseType() {
        btn_close.visibility = View.VISIBLE
        btn_back.visibility = View.GONE
    }

    fun setCloseAction(action: () -> Unit) {
        val listener = OnClickListener { action() }
        if (btn_close.isVisible) btn_close.setOnClickListener(listener)
        if (btn_back.isVisible) btn_back.setOnClickListener(listener)
    }
}