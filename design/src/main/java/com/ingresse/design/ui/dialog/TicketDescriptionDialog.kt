package com.ingresse.design.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.text.method.ScrollingMovementMethod
import android.view.Window
import android.widget.TextView
import com.ingresse.design.R
import com.ingresse.design.helper.ResourcesHelper

class TicketDescriptionDialog(context: Context): Dialog(context) {
    private val resourcesHelper = ResourcesHelper(context)
    init {
        val transparentColor = ColorDrawable(resourcesHelper.getColorHelper(R.color.transparent))
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_ticket_description)
        window?.setBackgroundDrawable(transparentColor)
        window?.attributes?.windowAnimations = R.style.BottomUpDialogAnimation
        findViewById<TextView>(R.id.lbl_description)?.movementMethod = ScrollingMovementMethod()
    }

    fun setInfos(event: String, group: String, ticket: String, description: String) {
        findViewById<TextView>(R.id.lbl_event_name)?.text = event
        findViewById<TextView>(R.id.lbl_group_name)?.text = group
        findViewById<TextView>(R.id.lbl_ticket_name)?.text = ticket
        findViewById<TextView>(R.id.lbl_description)?.text = description
    }
}