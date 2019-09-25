package com.ingresse.design.helper

import android.content.Context
import android.content.ContextWrapper
import android.graphics.drawable.Drawable
import android.os.Build
import android.widget.EditText
import androidx.annotation.StyleRes

@Suppress("DEPRECATION")
class ResourcesHelper(context: Context?) : ContextWrapper(context) {
    fun getColorHelper(id: Int): Int =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) resources.getColor(id, null)
            else resources.getColor(id)

    fun getDrawableHelper(id: Int): Drawable =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) resources.getDrawable(id, null)
            else resources.getDrawable(id)

    fun setTextAppearanceHelper(view: EditText, @StyleRes style: Int) =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) view.setTextAppearance(style)
            else view.setTextAppearance(this, style)
}