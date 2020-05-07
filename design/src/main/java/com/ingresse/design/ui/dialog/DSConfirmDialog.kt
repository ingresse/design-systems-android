package com.ingresse.design.ui.dialog

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.ViewGroup
import com.ingresse.design.R
import com.ingresse.design.helper.setVisible
import kotlinx.android.synthetic.main.ds_confirm_dialog.*

class DSConfirmDialog(context: Context): DSBaseDialog(R.layout.ds_confirm_dialog, context) {
    fun setInfos(title: String = "",
                 message: String = "",
                 positiveTitle: String? = "Sim",
                 negativeTitle: String? = "Não",
                 onPositiveClick: (() -> Unit),
                 onNegativeClick: (() -> Unit)) {

        lbl_dialog_title.text = title
        lbl_dialog_message.text = message
        btn_confirm.text = positiveTitle
        btn_cancel.text = negativeTitle

        btn_confirm.setOnClickListener { onPositiveClick() }
        btn_cancel.setOnClickListener { onNegativeClick() }
    }

    fun setEvent(eventName: String = "", dialogPoster: Drawable? = null) {
        lbl_event_name.setVisible(true)
        lbl_event_name.text = eventName

        val params = lbl_dialog_title.layoutParams as ViewGroup.MarginLayoutParams
        params.setMargins(params.leftMargin, 8, params.rightMargin, params.bottomMargin)

        val poster = dialogPoster ?: return
        iv_dialog_poster.setImageDrawable(poster)
        iv_dialog_poster.setVisible(true)
    }
}