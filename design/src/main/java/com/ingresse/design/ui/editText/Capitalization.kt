package com.ingresse.design.ui.editText

enum class Capitalization(val id: Int) {
    UPPERCASE(0),
    CAPITALIZED(1),
    LOWERCASE(2);

    companion object {
        fun fromId(id: Int) = values().find { it.id == id } ?: CAPITALIZED
    }
}