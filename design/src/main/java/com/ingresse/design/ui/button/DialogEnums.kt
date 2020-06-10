package com.ingresse.design.ui.button

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.ingresse.design.R

enum class PasskeyDialogStatus(@ColorRes val textColor: Int,
                               @DrawableRes val background: Int,
                               val buttonType: ButtonType
) {
    EMPTY(R.color.mercury_70, R.drawable.dialog_insert_passkey_empty_bg, ButtonType.CONFIRM),
    VALID(R.color.white, R.drawable.dialog_insert_passkey_valid_bg, ButtonType.CRYSTAL),
    INVALID(R.color.white, R.drawable.dialog_insert_passkey_invalid_bg, ButtonType.CRYSTAL)
}