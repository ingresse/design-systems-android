package com.ingresse.design.ui.component

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.ingresse.design.R
import com.ingresse.design.helper.FormatText
import kotlinx.android.synthetic.main.ds_credit_card_view.view.*

class DSCreditCardView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {
    init {
        inflate(context, R.layout.ds_credit_card_view, this)
    }

    fun setCardNumber(number: String) {
        lbl_credit_card_number.text = number
    }

    fun setCardName(name: String) {
        lbl_credit_card_name.text = name
    }

    fun setCardExpirationDate(date: String) {
        lbl_credit_card_expiration_date.text = date
    }
}