package com.ingresse.design.helper

import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment

fun Fragment.sendFragmentToFront() {
    val fragmentView = view ?: return
    ViewCompat.setTranslationZ(fragmentView, 100f)
}

fun Fragment.sendFragmentToBack() {
    val fragmentView = view ?: return
    ViewCompat.setTranslationZ(fragmentView, 0f)
}
