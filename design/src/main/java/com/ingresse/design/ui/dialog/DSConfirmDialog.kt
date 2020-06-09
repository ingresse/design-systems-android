package com.ingresse.design.ui.dialog

import android.content.Context
import android.graphics.drawable.Drawable
import com.ingresse.design.R
import com.ingresse.design.helper.setMarginByResources
import com.ingresse.design.helper.setVisible
import kotlinx.android.synthetic.main.ds_confirm_dialog.*

class DSConfirmDialog(context: Context): DSBaseDialog(R.layout.ds_confirm_dialog, context) {

    private var defaultPositiveBtn = context.getString(R.string.confirm_dialog_default_positive_btn)
    private var defaultNegativeBtn = context.getString(R.string.confirm_dialog_default_negative_btn)

    fun setInfos(title: String = "",
                 message: String = "",
                 positiveTitle: String = defaultPositiveBtn,
                 negativeTitle: String = defaultNegativeBtn,
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

        lbl_dialog_title.setMarginByResources(context, null, R.dimen.spacing_x2, null, null)

        val poster = dialogPoster ?: return
        iv_dialog_poster.setImageDrawable(poster)
        iv_dialog_poster.setVisible(true)
    }
}