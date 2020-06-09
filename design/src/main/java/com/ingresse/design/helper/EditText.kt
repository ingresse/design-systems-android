package com.ingresse.design.helper

import android.view.inputmethod.EditorInfo
import android.widget.EditText

fun EditText.onDone(callback: () -> Unit) {
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId != EditorInfo.IME_ACTION_DONE) { return@setOnEditorActionListener false }
        callback.invoke()
        return@setOnEditorActionListener true
    }
}