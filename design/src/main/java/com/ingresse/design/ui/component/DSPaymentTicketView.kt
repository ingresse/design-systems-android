package com.ingresse.design.ui.component

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.ingresse.design.R
import com.ingresse.design.helper.ResourcesHelper
import com.ingresse.design.helper.setColor
import com.ingresse.design.helper.setVisible
import com.ingresse.design.helper.toCurrency
import kotlinx.android.synthetic.main.ds_payment_ticket_view.view.*

class DSPaymentTicketView(context: Context, attrs: AttributeSet): ConstraintLayout(context, attrs) {
    val resHelper = ResourcesHelper(context)

    init { inflate(context, R.layout.ds_payment_ticket_view, this) }

    /**
     * Function to set the ticket information
     *
     * @param quantity - Quantity of tickets in cart
     * @param ticketName - Ticket's name
     * @param ticketUnitPrice - Price of one ticket unit
     * @param ticketUnitTax - Tax of one ticket unit
     * @param isPasskey - Variable to set ticket to passkey mode
     */
    fun setTicketInfo(quantity: Int,
                      ticketName: String,
                      ticketUnitPrice: Double,
                      ticketUnitTax: Double,
                      isPasskey: Boolean) {

        setTextColors(isPasskey)
        setBackgroundColors(isPasskey)

        if (ticketUnitPrice == 0.0) setFreeTicket()
        else setTicketPrice(ticketUnitPrice, ticketUnitTax)

        val ticketQuantityPattern = context.getString(R.string.payment_ticket_quantity)
        val ticketQuantity = String.format(ticketQuantityPattern, quantity)

        lbl_ticket_name.text = ticketName
        lbl_ticket_quantity.text = ticketQuantity
    }

    private fun setTicketPrice(price: Double,
                               tax: Double) {
        val totalPrice = price.plus(tax).toCurrency()
        val priceAndTaxPattern = context.getString(R.string.payment_ticket_price_tax)
        val priceAndTax = String.format(priceAndTaxPattern,
            price.toCurrency(),
            tax.toCurrency())

        lbl_ticket_price.text = totalPrice
        lbl_ticket_price_info.text = priceAndTax
        lbl_ticket_price.setVisible()
    }

    private fun setFreeTicket() {
        val freeTicket = context.getString(R.string.free_tickets)
        lbl_ticket_price_info.text = freeTicket
        lbl_ticket_price.setVisible(false)
    }

    private fun setTextColors(isPasskey: Boolean) {
        val textColor = if (isPasskey) R.color.white else R.color.mercury_70
        val color = resHelper.getColorHelper(textColor)

        lbl_ticket_name.setTextColor(color)
        lbl_ticket_price.setTextColor(color)
        lbl_ticket_price_info.setTextColor(color)
    }

    private fun setBackgroundColors(isPasskey: Boolean) {
        val quantityColor = if (isPasskey) R.color.mint_dark else R.color.ocean
        val backgroundColor = if (isPasskey) R.color.mint else R.color.desert_storm

        (lbl_ticket_quantity.background as GradientDrawable).setColor(context, quantityColor)
        (layout_ticket_info.background as GradientDrawable).setColor(context, backgroundColor)
    }
}