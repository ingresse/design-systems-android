package com.ingresse.design.ui.editText

enum class Capitalization(val id: Int) {
    UPPERCASE(0), CAPITALIZED(1), LOWERCASE(2);

    companion object {
        private val all = listOf(UPPERCASE, CAPITALIZED, LOWERCASE)
        fun fromId(id: Int) = all.firstOrNull { it.id == id } ?: CAPITALIZED
    }
}