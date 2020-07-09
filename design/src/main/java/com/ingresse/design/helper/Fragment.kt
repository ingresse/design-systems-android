package com.ingresse.design.helper

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
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

fun Fragment.hideKeyboard() {
    val ctx = context ?: return
    val currentView = view ?: View(ctx)
    val inputManager = ctx.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    inputManager?.hideSoftInputFromWindow(currentView.windowToken, 0)
}