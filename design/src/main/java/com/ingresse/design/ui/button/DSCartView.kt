package com.ingresse.design.ui.button

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.ingresse.design.R
import com.ingresse.design.helper.animateValue
import kotlinx.android.synthetic.main.ds_cart_view.view.*
import java.text.MessageFormat
import java.text.NumberFormat

class DSCartView(context: Context, attrs: AttributeSet): RelativeLayout(context, attrs) {
    val items = mutableListOf<Any>()
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
        lbl_cart_info.animateValue(oldValue, value, {
            val value = it.toDouble().toCurrency()
            val message = MessageFormat.format(resources.getString(R.string.cart_info), quantity, value)
            lbl_cart_info.text = message
        }, { oldValue = value }, 300)
    }
}

fun Double.toCurrency(): String {
    val formatter = NumberFormat.getCurrencyInstance()
    return formatter.format(this)
}