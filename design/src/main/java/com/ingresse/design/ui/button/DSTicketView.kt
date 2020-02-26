package com.ingresse.design.ui.button

import android.content.Context
import android.content.res.Resources
import android.graphics.Rect
import android.util.AttributeSet
import android.util.TypedValue
import android.view.TouchDelegate
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.isVisible
import com.ingresse.design.R
import com.ingresse.design.helper.ResourcesHelper
import kotlinx.android.synthetic.main.ds_ticket_view.view.*

class DSTicketView(context: Context, attrs: AttributeSet): LinearLayout(context, attrs) {
    private var selected: Boolean = false
    private var max: Int = 99
    private var min: Int = 0
    private var passkey: String = ""
    private var haveDescription: Boolean = false
    private var showDates: Boolean = false
    private var ticketName: String = ""

    private val resourcesHelper = ResourcesHelper(context)

    init {
        inflate(context, R.layout.ds_ticket_view, this)
        updateState()
        setupButtons()
    }

    override fun setSelected(isSelected: Boolean) {
        selected = isSelected
        ticket_price.isSelected = selected
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

    fun setTicket(
        name: String,
        max: Int = 99,
        min: Int = 0,
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
        lbl_ticket_name.text = ticketName
        updateState()
    }

    private fun updateState() {
        updateBackground()
        updateTextColor()
        updateViews()
        updateBackground()
    }

    private fun setupButtons() {
        ticket_unit_controller.setOnPlusClickListener {
            if (ticket_unit_controller.count == max) return@setOnPlusClickListener
            ticket_unit_controller.plusStep = if (ticket_unit_controller.count < min) min else 1
            ticket_unit_controller.plus()

            ticket_info_min.isVisible = min != 0 && ticket_unit_controller.count <= min
            ticket_info_max.isVisible = ticket_unit_controller.count == max

            selected = ticket_unit_controller.count > 0
            updateState()
        }

        ticket_unit_controller.setOnMinusClickListener {
            if (ticket_unit_controller.count == 0) return@setOnMinusClickListener
            ticket_unit_controller.minusStep = if (ticket_unit_controller.count == min) min else 1
            ticket_unit_controller.minus()

            ticket_info_min.isVisible = min != 0 && ticket_unit_controller.count <= min
            ticket_info_max.isVisible = ticket_unit_controller.count == max

            selected = ticket_unit_controller.count > 0
            updateState()
        }
    }

    private fun updateViews() {
        ticket_info_description.isVisible = haveDescription

        ticket_info_passkey.isVisible = passkey.isNotEmpty()
        ticket_info_passkey.setPasskey(passkey)

        ticket_info_min.isVisible = min != 0 && ticket_unit_controller.count <= min
        ticket_info_min.setMin(min)

        ticket_info_max.isVisible = ticket_unit_controller.count == max
        ticket_info_max.setMax(max)

        layout_dates.isVisible = showDates
    }

    private fun updateTextColor() {
        val nameColorRes = if (selected) R.color.white else R.color.mercury_70
        lbl_ticket_name.setTextColor(resourcesHelper.getColorHelper(nameColorRes))
        ticket_price.isSelected = selected
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
            datesIsLastView && selected -> R.drawable.ds_ticket_info_description_bottom_bg
            datesIsLastView && !selected -> R.drawable.ds_layout_dates_bottom_bg
            selected -> R.color.ocean_crystal
            else -> R.color.off_white
        }

        layout_dates.background = resourcesHelper.getDrawableHelper(recyclerBGRes)

        val ticketBG = when {
            lastView == null && selected -> R.drawable.ds_ticket_selected_bg_rounded
            lastView == null && !selected -> R.drawable.ds_ticket_bg_rounded
            lastView != null && selected -> R.drawable.ds_ticket_selected_bg
            else -> R.drawable.ds_ticket_bg
        }

        layout_ds_ticket_view.background = resourcesHelper.getDrawableHelper(ticketBG)
    }
}