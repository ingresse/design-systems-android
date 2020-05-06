package com.ingresse.design.ui.dialog

import android.content.Context
import com.ingresse.design.R
import com.ingresse.design.helper.animateBackground
import com.ingresse.design.helper.animateColor
import com.ingresse.design.helper.setInvisible
import com.ingresse.design.ui.button.PasskeyDialogStatus
import kotlinx.android.synthetic.main.dialog_insert_paskey.*

class InsertPasskeyDialog(context: Context): DSBaseDialog(R.layout.dialog_insert_paskey, context) {
    var dialogStatus: PasskeyDialogStatus = PasskeyDialogStatus.EMPTY
    set(value) {
        field = value
        setupComponents()
    }

    var isLoading: Boolean = false
    set(value) {
        field = value
        progress_paskey.setInvisible(!value)
        edt_passkey.setInvisible(value)
    }

    private fun setupComponents() {
        layout_dialog.animateBackground(dialogStatus.background, context)
        lbl_title.animateColor(dialogStatus.textColor, context)
        edt_passkey.animateColor(dialogStatus.textColor, context)
        btn_confirm.setStyle(dialogStatus.buttonType)

        btn_confirm.setInvisible(dialogStatus == PasskeyDialogStatus.VALID)
        btn_valid_passkey.setInvisible(dialogStatus != PasskeyDialogStatus.VALID)
    }

    fun setOnClick(listener: (String) -> Unit) =
        btn_confirm?.setOnClickListener { listener(edt_passkey.text.toString()) }

    fun setValidPasskeyClick(listener: (String) -> Unit) =
        btn_valid_passkey.setOnClickListener { listener(edt_passkey.text.toString()) }
}