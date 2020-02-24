package com.ingresse.design.ui.button

import android.content.Context
import android.os.Build
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.TextAppearanceSpan
import android.util.AttributeSet
import android.widget.LinearLayout
import com.ingresse.design.R
import kotlinx.android.synthetic.main.ds_ticket_price.view.*

class DSTicketPrice(context: Context, attrs: AttributeSet): LinearLayout(context, attrs) {
    private var price: Double
    private var tax: Double
    private var selected: Boolean
    private var singleLine: Boolean

    init {
        inflate(context, R.layout.ds_ticket_price, this)
        val array = context.theme.obtainStyledAttributes(attrs, R.styleable.DSTicketPrice, 0, 0)
        selected = array.getBoolean(R.styleable.DSTicketPrice_selected, false)
        singleLine = array.getBoolean(R.styleable.DSTicketPrice_singleLine, true)
        price = array.getFloat(R.styleable.DSTicketPrice_price, 0.0f).toDouble()
        tax = array.getFloat(R.styleable.DSTicketPrice_tax, 0.0f).toDouble()
        updateText()
        array.recycle()
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
        info.setSpan(taxAppearance, info.length - taxString.length, info.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        val textColorRes = if (selected) R.color.white else R.color.mercury_70
        val priceColor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) context.getColor(textColorRes)
        else resources.getColor(textColorRes)

        lbl_ticket_price.setTextColor(priceColor)
        lbl_ticket_price.text = SpannableStringBuilder().append(info)
    }
}