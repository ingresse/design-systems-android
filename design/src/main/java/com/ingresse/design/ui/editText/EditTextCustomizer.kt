package com.ingresse.design.ui.editText

import android.graphics.PorterDuff
import android.os.Build
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat

fun EditText.setHandleColor(@ColorInt color: Int) {
    try {
        val selectHandle = TextView::class.java.getDeclaredField("mTextSelectHandleRes")
        val textEditor = TextView::class.java.getDeclaredField("mEditor")

        selectHandle.isAccessible = true
        textEditor.isAccessible = true

        val selectHandleDrawable = selectHandle.getInt(this)
        val editor = textEditor.get(this)

        val drawable = ContextCompat.getDrawable(this.context, selectHandleDrawable)!!
        drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) return

        val centerHandle = editor.javaClass.getDeclaredField("mSelectHandleCenter")
        val leftHandle = editor.javaClass.getDeclaredField("mSelectHandleLeft")
        val rightHandle = editor.javaClass.getDeclaredField("mSelectHandleRight")

        centerHandle.isAccessible = true
        leftHandle.isAccessible = true
        rightHandle.isAccessible = true

        centerHandle.set(editor, drawable)
        leftHandle.set(editor, drawable)
        rightHandle.set(editor, drawable)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun EditText.setCursorColor(@ColorInt color: Int) {
    try {
        val textCursor = TextView::class.java.getDeclaredField("mCursorDrawableRes")
        val textEditor = TextView::class.java.getDeclaredField("mEditor")

        textCursor.isAccessible = true
        textEditor.isAccessible = true

        val cursorDrawable = textCursor.getInt(this)
        val editor = textEditor.get(this)

        val drawable = ContextCompat.getDrawable(this.context, cursorDrawable)
        drawable?.setColorFilter(color, PorterDuff.Mode.SRC_IN)
        val drawables = arrayOf(drawable, drawable)

        val textCursorDrawable = editor.javaClass.getDeclaredField("mCursorDrawable")
        textCursorDrawable.isAccessible = true
        textCursorDrawable.set(editor, drawables)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}