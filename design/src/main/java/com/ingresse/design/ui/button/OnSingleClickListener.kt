package com.ingresse.design.ui.button

import android.os.SystemClock
import android.view.View

private const val CLICK_INTERVAL = 1000

abstract class OnSingleClickListener: View.OnClickListener {
    private var lastClickTime = 0L

    abstract fun onSingleClick(v: View?)

    override fun onClick(v: View?) {
        val current = SystemClock.uptimeMillis()
        val elapsed = current - lastClickTime

        if (elapsed <= CLICK_INTERVAL) return
        lastClickTime = elapsed
        onSingleClick(v)
    }
}

fun View.setOnSingleClickListener(listener: () -> Unit) = setOnClickListener(object: OnSingleClickListener() {
    override fun onSingleClick(v: View?) = listener()
})