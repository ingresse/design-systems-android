package com.ingresse.design.ui.dialog

import android.content.Context
import com.ingresse.design.R
import com.ingresse.design.helper.animateColor
import com.ingresse.design.helper.changeBackground
import com.ingresse.design.helper.setInvisible
import com.ingresse.design.ui.button.PasskeyDialogStatus
import kotlinx.android.synthetic.main.dialog_insert_paskey.*
import java.text.MessageFormat

class InsertPasskeyDialog(context: Context): DSBaseDialog(R.layout.dialog_insert_paskey, context) {
    private var dialogStatus: PasskeyDialogStatus = PasskeyDialogStatus.EMPTY
    var isLoading: Boolean = false
    set(value) {
        field = value
        progress_paskey.setInvisible(!value)
        edt_passkey.setInvisible(value)
    }

    init { setupComponents() }

    private fun setupComponents(withAnimation: Boolean = true) {
        layout_dialog.changeBackground(dialogStatus.background, context, withAnimation = withAnimation)
        lbl_title.animateColor(dialogStatus.textColor, context)
        edt_passkey.animateColor(dialogStatus.textColor, context)
        btn_confirm.setStyle(dialogStatus.buttonType)

        edt_passkey.isEnabled = dialogStatus != PasskeyDialogStatus.VALID

        btn_confirm.setInvisible(dialogStatus == PasskeyDialogStatus.VALID)
        btn_valid_passkey.setInvisible(dialogStatus != PasskeyDialogStatus.VALID)

        overlay_layout.setOnClickListener { dismiss() }
        card_view.setOnClickListener { dismiss() }
    }

    override fun dismiss() {
        super.dismiss()
        isLoading = false
        dialogStatus = PasskeyDialogStatus.EMPTY
        edt_passkey.setText("")
        setupComponents(false)
    }

    fun showDialogStatusBy(tickets: Int) {
        if (tickets > 0) {
            dialogStatus = PasskeyDialogStatus.VALID
            val message = MessageFormat.format(
                context.resources.getString(R.string.count_passkey_tickets), tickets)

            top_snackbar.showPositiveMessage(message)
            setupComponents()
            return
        }

        top_snackbar.showNegativeMessage(context.getString(R.string.invalid_passkey))
        dialogStatus = PasskeyDialogStatus.INVALID
        setupComponents()
    }

    fun setOnClick(listener: (String) -> Unit) =
        btn_confirm?.setOnClickListener { listener(edt_passkey.text.toString()) }

    fun setValidPasskeyClick(listener: (String) -> Unit) =
        btn_valid_passkey.setOnClickListener { listener(edt_passkey.text.toString()) }
}