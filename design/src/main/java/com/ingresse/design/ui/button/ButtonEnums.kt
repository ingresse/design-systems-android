package com.ingresse.design.ui.button

import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import com.ingresse.design.R

enum class ButtonType(val id: Int) {
    PRIMARY(0), ACCENT(1);

    companion object {
        private val all = listOf(PRIMARY, ACCENT)
        fun fromId(id: Int) = all.firstOrNull { it.id == id } ?: PRIMARY
    }
}

enum class ButtonStyle(val id: Int, @ColorRes val normal: Int, @ColorRes val pressed: Int) {
    PRIMARY(0, R.color.tangerine, R.color.tangerine_dark),
    SECONDARY(1, R.color.ocean, R.color.ocean_dark),
    CONFIRM(2, R.color.bamboo, R.color.bamboo_dark),
    DESTRUCTIVE(3, R.color.ruby, R.color.ruby_dark),
    CRYSTAL(4, R.color.white_20, R.color.white_10);

    companion object {
        private val all = listOf(PRIMARY, SECONDARY, CONFIRM, DESTRUCTIVE, CRYSTAL)
        fun fromId(id: Int) = all.firstOrNull { it.id == id } ?: PRIMARY
    }
}

enum class ButtonSize(val id: Int, @DimenRes val height: Int) {
    SMALL(0, R.dimen.btn_small_height),
    NORMAL(1, R.dimen.btn_normal_height);

    companion object {
        private val all = listOf(SMALL, NORMAL)
        fun fromId(id: Int) = all.firstOrNull { it.id == id } ?: NORMAL
    }
}
