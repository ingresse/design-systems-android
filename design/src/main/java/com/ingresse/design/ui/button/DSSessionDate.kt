package com.ingresse.design.ui.button

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.ingresse.design.R

class DSSessionDate(context: Context, attrs: AttributeSet): LinearLayout(context, attrs) {

    init {
        inflate(context, R.layout.ds_session_date, this)
    }
}