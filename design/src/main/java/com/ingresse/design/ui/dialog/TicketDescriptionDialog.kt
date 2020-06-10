package com.ingresse.design.ui.dialog

import android.content.Context
import android.text.method.ScrollingMovementMethod
import com.ingresse.design.R
import kotlinx.android.synthetic.main.dialog_ticket_description.*

class TicketDescriptionDialog(context: Context): DSBaseDialog(R.layout.dialog_ticket_description, context) {
    fun setInfos(event: String, group: String, ticket: String, description: String) {
        lbl_description.movementMethod = ScrollingMovementMethod()
        lbl_event_name.text = event
        lbl_group_name.text = group
        lbl_ticket_name.text = ticket
        lbl_description.text = description
    }
}