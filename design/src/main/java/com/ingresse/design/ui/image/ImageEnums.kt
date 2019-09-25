package com.ingresse.design.ui.image

import androidx.annotation.ColorRes
import com.ingresse.design.R
import jp.wasabeef.glide.transformations.BlurTransformation

enum class ImageSize(val id: Int, val multiplier: Float) {
    ORIGINAL(0, 0f),
    SMALL(1, 0.1f);

    companion object {
        fun fromId(id: Int) = values().find { it.id == id } ?: ORIGINAL
    }
}

enum class BlurIntensity(val id: Int, val blur: BlurTransformation) {
    ZERO(0, BlurTransformation(0, 0)),
    STANDARD(1, BlurTransformation(25, 10));

    companion object {
        fun fromId(id: Int) = values().find { it.id == id } ?: ZERO
    }
}

enum class AlphaIntensity(val id: Int, @ColorRes val color: Int) {
    ZERO(0, R.color.transparent),
    WEAK(1, R.color.black_20),
    MEDIUM(2, R.color.black_40),
    STRONG(3, R.color.black_60);

    companion object {
        fun findId(id: Int) = values().find { it.id == id } ?: ZERO
    }
}