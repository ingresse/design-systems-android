package com.ingresse.design.ui.button

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.ingresse.design.R
import com.ingresse.design.helper.animateBackground
import com.ingresse.design.helper.animateColor
import com.ingresse.design.ui.adapters.BaseAdapter
import kotlinx.android.synthetic.main.ds_ticket_view.view.*

class DSTicketView(context: Context, attrs: AttributeSet): LinearLayout(context, attrs) {
    private var max: Int = 99
    private var min: Int = 0
    private var passkey: String = ""
    private var haveDescription: Boolean = false
    private var showDates: Boolean = false
    private var ticketName: String = ""
    private var datesAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>? = null
    private var quantityListener: ((Int) -> Unit)? = null

    var quantity: Int
    get() = ticket_unit_controller.count
    set(value) {
        ticket_unit_controller.count = value
        updateState()
    }

    init {
        inflate(context, R.layout.ds_ticket_view, this)
        setupButtons()
        updateState()
    }

    override fun setSelected(isSelected: Boolean) {
        super.setSelected(isSelected)
        ticket_price.isSelected = isSelected
        updateBackground()
    }

    fun setTicketname(ticketName: String) {
        this.ticketName = ticketName
        lbl_ticket_name.text = ticketName
    }

    fun setMax(max: Int) {
        this.max = max
        updateState()
    }

    fun setMin(min: Int) {
        this.min = min
        updateState()
    }

    fun setPasskey(passkey: String) {
        this.passkey = passkey
        updateState()
    }

    fun setHaveDescription(haveDescription: Boolean) {
        this.haveDescription = haveDescription
        updateState()
    }

    fun <T>setDatesAdapter(adapter: BaseAdapter<T>) {
        recycler_dates.adapter = adapter
    }

    fun setOnDescriptionInfoClick(listener: () -> Unit) = ticket_info_description.setOnClickListener { listener() }

    fun setQuantityListener(listener: (quantity: Int) -> Unit) { quantityListener = listener }

    /**
     * Method to set ticket infos to TicketView
     *
     * @param name - name of ticket
     * @param max - limit of selected tickets
     * @param min - min of selected tickets
     * @param passkey - passkey code related to ticket (if have)
     * @param haveDescription - if true show TicketInfo description label
     * @param showDates - if true show carousel with ticket dates
     */
    fun setTicket(
        name: String,
        max: Int = 99,
        min: Int = 0,
        price: Double = 0.0,
        tax: Double = 0.0,
        passkey: String = "",
        haveDescription: Boolean = false,
        showDates: Boolean = false
    ) {
        this.ticketName = name
        this.max = max
        this.min = min
        this.passkey = passkey
        this.haveDescription = haveDescription
        this.showDates = showDates
        ticket_price.setValues(price, tax)
        lbl_ticket_name.text = ticketName
        updateState()
    }

    private fun updateState() {
        isSelected = ticket_unit_controller.count > 0
        updateBackground()
        updateTextColor()
        updateViews()
        updateBackground()
    }

    private fun setupButtons() {
        ticket_unit_controller.setOnPlusClickListener listener@ {
            if (ticket_unit_controller.count == max) return@listener
            ticket_unit_controller.plusStep = if (ticket_unit_controller.count < min) min else 1
            ticket_unit_controller.plus()
            updateState()
            quantityListener?.invoke(quantity)
        }

        ticket_unit_controller.setOnMinusClickListener listener@ {
            if (ticket_unit_controller.count == 0) return@listener
            ticket_unit_controller.minusStep = if (ticket_unit_controller.count == min) min else 1
            ticket_unit_controller.minus()
            updateState()
            quantityListener?.invoke(quantity)
        }
    }

    private fun updateViews() {
        ticket_info_description.setDescription()
        ticket_info_description.isVisible = haveDescription

        ticket_info_passkey.isVisible = passkey.isNotEmpty()
        ticket_info_passkey.setPasskey(passkey)

        ticket_info_min.isVisible = min > 1 && ticket_unit_controller.count <= min
        ticket_info_min.setMin(min)

        ticket_info_max.isVisible = ticket_unit_controller.count == max
        ticket_info_max.setMax(max)

        layout_dates.isVisible = showDates
    }

    private fun updateTextColor() {
        val nameColorRes = if (isSelected) R.color.white else R.color.mercury_70
        lbl_ticket_name.animateColor(nameColorRes, context)
        ticket_price.isSelected = isSelected
    }

    private fun updateBackground() {
        val ticketInfos =
            listOf(ticket_info_description, ticket_info_passkey, ticket_info_min, ticket_info_max)
        
        val lastView = ticketInfos.lastOrNull { it != null && it.isVisible }
        ticket_info_description.isBottomRounded(lastView == ticket_info_description)
        ticket_info_passkey.isBottomRounded(lastView == ticket_info_passkey)
        ticket_info_min.isBottomRounded(lastView == ticket_info_min)
        ticket_info_max.isBottomRounded(lastView == ticket_info_max)

        val datesIsLastView = (lastView == null && layout_dates.isVisible)
        val recyclerBGRes = when {
            datesIsLastView && isSelected -> R.drawable.ds_ticket_info_description_bottom_bg
            datesIsLastView && !isSelected -> R.drawable.ds_layout_dates_bottom_bg
            isSelected -> R.color.ocean_crystal
            else -> R.color.off_white
        }

        layout_dates.animateBackground(recyclerBGRes, context)

        val ticketBG = when {
            (!datesIsLastView && lastView == null) && isSelected -> R.drawable.ds_ticket_selected_bg_rounded
            (!datesIsLastView && lastView == null) && !isSelected -> R.drawable.ds_ticket_bg_rounded
            (datesIsLastView || lastView != null) && isSelected -> R.drawable.ds_ticket_selected_bg
            else -> R.drawable.ds_ticket_bg
        }

        layout_ds_ticket_view.animateBackground(ticketBG, context)
    }
}