package com.ingresse.design.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.Window
import androidx.annotation.LayoutRes
import com.ingresse.design.R
import com.ingresse.design.helper.ResourcesHelper

open class DSBaseDialog(@LayoutRes layout: Int, context: Context): Dialog(context) {
    private val resourcesHelper = ResourcesHelper(context)
    init {
        val transparentColor = ColorDrawable(resourcesHelper.getColorHelper(R.color.transparent))
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.setContentView(layout)
        window?.setBackgroundDrawable(transparentColor)
        window?.attributes?.windowAnimations = R.style.BottomUpDialogAnimation
    }
}