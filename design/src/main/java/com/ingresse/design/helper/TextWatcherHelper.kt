package com.ingresse.design.helper

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class Watcher(private val callback: WatcherCallback) : TextWatcher {
    override fun afterTextChanged(s: Editable?) { }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        callback.listen(s?.toString() ?: "")
    }
}

interface WatcherCallback {
    fun listen(string: String)
}

class Watch(val text: EditText, val action: (String) -> Unit) : WatcherCallback {
    override fun listen(string: String) {
        action(string)
    }

    private val watcher = Watcher(this)

    init {
        text.addTextChangedListener(watcher)
    }

    fun remove() {
        text.removeTextChangedListener(watcher)
    }
}

abstract class TextWatcherMin: TextWatcher {
    override fun afterTextChanged(s: Editable?) {}
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
}