package com.ingresse.design.ui.dialog

import android.content.Context
import android.text.method.ScrollingMovementMethod
import android.widget.TextView
import com.ingresse.design.R

class TicketDescriptionDialog(context: Context): DSBaseDialog(R.layout.dialog_ticket_description, context) {
    fun setInfos(event: String, group: String, ticket: String, description: String) {
        findViewById<TextView>(R.id.lbl_description)?.movementMethod = ScrollingMovementMethod()
        findViewById<TextView>(R.id.lbl_event_name)?.text = event
        findViewById<TextView>(R.id.lbl_group_name)?.text = group
        findViewById<TextView>(R.id.lbl_ticket_name)?.text = ticket
        findViewById<TextView>(R.id.lbl_description)?.text = description
    }
}