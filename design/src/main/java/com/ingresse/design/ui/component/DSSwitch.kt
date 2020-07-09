package com.ingresse.design.ui.component

import android.content.Context
import android.util.AttributeSet
import android.widget.Switch
import com.ingresse.design.R
import com.ingresse.design.helper.ResourcesHelper

class DSSwitch(context: Context, private val attributes: AttributeSet) : Switch(context, attributes) {
    init {
        val resHelper = ResourcesHelper(context)
        thumbDrawable = resHelper.getDrawableHelper(R.drawable.ds_switch_thumb)
        trackDrawable = resHelper.getDrawableHelper(R.drawable.ds_switch_track)
    }
}