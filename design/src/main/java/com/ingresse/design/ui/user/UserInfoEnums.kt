package com.ingresse.design.ui.user

import androidx.annotation.DrawableRes
import com.ingresse.design.R

enum class DSUserInfoCardType(val id: Int, @DrawableRes val icon: Int?) {
    EDIT(0, R.drawable.ic_edit_ocean),
    SEND(1, R.drawable.ic_send_ocean),
    CANCEL(2, R.drawable.ic_cross_ocean),
    UNDEFINED(3, null);

    companion object {
        fun getById(id: Int) = values().firstOrNull { it.id == id } ?: UNDEFINED
    }
}