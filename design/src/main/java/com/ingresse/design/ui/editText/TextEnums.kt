package com.ingresse.design.ui.editText

import android.text.InputType

enum class Capitalization(val id: Int) {
    UPPERCASE(0),
    CAPITALIZED(1),
    LOWERCASE(2);

    companion object {
        fun fromId(id: Int) = values().find { it.id == id } ?: CAPITALIZED
    }
}

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

enum class TextFormatType(val id: Int,
                          val minCharFormatted: Int?,
                          val maxCharFormatted: Int?,
                          val minChar: Int?,
                          val maxChar: Int?) {
    NONE(0, null, null, null, null),
    PHONE(1, 14, 15, 10, 11),
    ZIPCODE(2, 9, 9, 8, 8),
    CPF(3, 14, 14, 11, 11),
    CNPJ(4, 18, 18, 14, 14),
    MIXED_CPF_CNPJ(5, 14, 18, 11, 14),
    CREDIT_CARD(6, 14, null, 12, null),
    SIMPLE_DATE(7, 5, 5, 4, 4);

    companion object {
        fun fromId(id: Int) = values().find { it.id == id } ?: NONE
    }
}