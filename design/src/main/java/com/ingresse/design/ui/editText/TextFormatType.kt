package com.ingresse.design.ui.editText

enum class TextFormatType(
    val id: Int,
    val minCharFormatted: Int?,
    val maxCharFormatted: Int?,
    val minChar: Int?,
    val maxChar: Int?
) {
    NONE(0, null, null, null, null),
    PHONE(1, 14, 15, 10, 11),
    ZIPCODE(2, 9, 9, 8, 8),
    CPF(3, 14, 14, 11, 11),
    CNPJ(4, 18, 18, 14, 14),
    MIXED_CPF_CNPJ(5, 14, 18, 11, 14),
    CREDIT_CARD(6, 14, 24, 10, 20),
    SIMPLE_DATE(7, 5, 5, 4, 4),
    PHONE_9(8, 15, 15, 11, 11),
    INTERNATIONAL_PHONE(9, null, null, null, null),
    CREDIT_CARD_CVV(10, null, null, 3, 4),
    DATE( 11, 10, 10, 8, 8);

    companion object {
        fun fromId(id: Int) = values().find { it.id == id } ?: NONE
    }
}
