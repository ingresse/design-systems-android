package com.ingresse.design.ui.button

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.TextAppearanceSpan
import android.util.AttributeSet
import android.widget.LinearLayout
import com.ingresse.design.R
import com.ingresse.design.helper.animateColor
import kotlinx.android.synthetic.main.ds_ticket_price.view.*

class DSTicketPrice(context: Context, attrs: AttributeSet): LinearLayout(context, attrs) {
    private var price: Double = 0.0
    private var tax: Double = 0.0
    private var selected: Boolean = false
    private var singleLine: Boolean = true

    init {
        inflate(context, R.layout.ds_ticket_price, this)
        updateText()
    }

    fun setPrice(price: Double) {
        this.price = price
        updateText()
    }

    fun setTax(tax: Double) {
        this.tax = tax
        updateText()
    }

    override fun setSelected(isSelected: Boolean) {
        selected = isSelected
        updateText()
    }

    fun isSingleLine(singleLine: Boolean) {
        this.singleLine = singleLine
        updateText()
    }

    private fun updateText() {
        val priceString = String.format(resources.getString(R.string.ticket_price), price)
        val taxString = String.format(resources.getString(R.string.ticket_tax_price), tax)
        val lblText = if (singleLine) "$priceString $taxString" else "$priceString\n$taxString"

        val textStyle = if (selected) R.style.DSTextStyle_TicketPrice_Tax_Selected
        else R.style.DSTextStyle_TicketPrice_Tax

        val taxAppearance = TextAppearanceSpan(context, textStyle)
        val info = SpannableString(lblText)
        info.setSpan(
            taxAppearance,
            info.length - taxString.length,
            info.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        val textColorRes = if (selected) R.color.white else R.color.mercury_70
        lbl_ticket_price.animateColor(textColorRes, context)
        lbl_ticket_price.text = SpannableStringBuilder().append(info)
    }
}