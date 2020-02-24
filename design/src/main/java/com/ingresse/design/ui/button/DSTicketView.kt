package com.ingresse.design.ui.button

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.core.view.isVisible
import com.ingresse.design.R
import kotlinx.android.synthetic.main.ds_ticket_view.view.*

class DSTicketView(context: Context, attrs: AttributeSet): LinearLayout(context, attrs) {
    private var selected: Boolean
    private var max: Int
    private var min: Int
    private var passkey: String
    private var haveDescription: Boolean
    private var singleDate: Boolean

    init {
        inflate(context, R.layout.ds_ticket_view, this)
        val array = context.theme.obtainStyledAttributes(attrs, R.styleable.DSTicketView, 0, 0)
        selected = array.getBoolean(R.styleable.DSTicketView_selected, false)
        max = array.getInt(R.styleable.DSTicketView_max, 0)
        min = array.getInt(R.styleable.DSTicketView_min, 0)
        passkey = array.getString(R.styleable.DSTicketView_passkey).orEmpty()
        haveDescription = array.getBoolean(R.styleable.DSTicketView_have_description, false)
        singleDate = array.getBoolean(R.styleable.DSTicketView_single_date, true)

        updateState()
    }

    override fun setSelected(isSelected: Boolean) {
        selected = isSelected
    }

    private fun updateState() {
        updateBackground()
        updateTextColor()
        updateViews()
        updateLastViewBackground()
    }

    private fun updateViews() {
        ticket_info_description.isVisible = haveDescription

        ticket_info_passkey.isVisible = passkey.isNotEmpty()
        ticket_info_passkey.setPasskey(passkey)

        ticket_info_min.isVisible = min != 0
        ticket_info_min.setMin(min)

        ticket_info_max.isVisible = max != 0
        ticket_info_max.setMax(max)

        layout_dates.isVisible = !singleDate
    }

    private fun updateTextColor() {
        val nameColorRes = if (selected) R.color.white else R.color.mercury_70
        val nameColor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) context.getColor(nameColorRes)
        else resources.getColor(nameColorRes)

        lbl_ticket_name.setTextColor(nameColor)
        ticket_price.isSelected = selected
    }

    private fun updateBackground() {
        val backgroundRes = if (selected) R.drawable.ds_ticket_selected_bg else R.drawable.ds_ticket_bg
        val backgroundColor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) context.getDrawable(backgroundRes)
        else resources.getDrawable(backgroundRes)
        layout_ds_ticket_view.background = backgroundColor
    }

    private fun updateLastViewBackground() {
        val ticketInfos = listOf(ticket_info_description, ticket_info_passkey, ticket_info_min, ticket_info_max)
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

        val recyclerBGColor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) context.getDrawable(recyclerBGRes)
        else resources.getDrawable(recyclerBGRes)
        layout_dates.background = recyclerBGColor
    }
}