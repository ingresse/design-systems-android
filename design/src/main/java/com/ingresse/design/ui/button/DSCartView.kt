package com.ingresse.design.ui.button

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.ingresse.design.R
import com.ingresse.design.helper.animateValue
import com.ingresse.design.helper.toCurrency
import kotlinx.android.synthetic.main.ds_cart_view.view.*
import java.text.MessageFormat

class DSCartView(context: Context, attrs: AttributeSet): RelativeLayout(context, attrs) {
    var quantity = 0
    set(value) {
        field = value
        updateTexts()
    }

    var oldValue = 0.0
    var value = 0.0
    set(value) {
        field = value
        updateTexts()
    }
    
    init {
        inflate(context, R.layout.ds_cart_view, this)
        updateTexts()
    }
    
    private fun updateTexts() {
        lbl_cart_info.animateValue(from = oldValue, to =  value, listener = {
            val value = it.toDouble().toCurrency()
            val messagePattern = resources.getString(R.string.cart_info)
            val message = MessageFormat.format(messagePattern, quantity, value)
            lbl_cart_info.text = message
        }, onEnd = { oldValue = value }, duration = 300)
    }

    fun setOnClick(listener: () -> Unit) = btn_confirm.setOnClickListener { listener() }
}