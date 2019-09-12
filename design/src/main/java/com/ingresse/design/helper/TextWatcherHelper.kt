package com.ingresse.design.helper

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class Watcher(private val callback: WatcherCallback) : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun afterTextChanged(s: Editable?) {}
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        callback.listen(s?.toString().orEmpty())
    }
}

interface WatcherCallback {
    fun listen(string: String) {}
}

class Watch(val text: EditText) {
    private var watcher: TextWatcher? = null

    fun listen(action: (String) -> Unit) {
        val callback = object : WatcherCallback {
            override fun listen(string: String) { action(string) }
        }

        watcher = Watcher(callback)
        text.addTextChangedListener(watcher)
    }

    fun remove() = text.removeTextChangedListener(watcher)
}

fun singleListener(action: (String) -> Unit): Watcher {
    val callback = object : WatcherCallback {
        override fun listen(string: String) { action(string) }
    }

    return Watcher(callback)
}

abstract class TextWatcherMin: TextWatcher {
    override fun afterTextChanged(s: Editable?) {}
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
}