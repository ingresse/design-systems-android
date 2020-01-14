package com.ingresse.design.ui.editText

import android.text.InputType

enum class TextInputType(val id: Int, val type: Int) {
    NONE(0, InputType.TYPE_NULL),
    TEXT(1, InputType.TYPE_CLASS_TEXT),
    EMAIL(2, InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS),
    PERSON_NAME(3, InputType.TYPE_TEXT_VARIATION_PERSON_NAME),
    NO_SUGGESTIONS(4, InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS),
    PHONE(5, InputType.TYPE_CLASS_PHONE),
    NUMBER(6, InputType.TYPE_CLASS_NUMBER),
    ZIPCODE(7, InputType.TYPE_TEXT_VARIATION_POSTAL_ADDRESS),
    DATE(8, InputType.TYPE_DATETIME_VARIATION_DATE);

    companion object {
        fun fromId(id: Int) = values().find { it.id == id } ?: NONE
    }
}