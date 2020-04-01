package com.ingresse.design.ui.header

import android.content.Context
import android.util.AttributeSet
import android.view.View.OnClickListener
import android.widget.FrameLayout
import android.widget.ImageView
import com.ingresse.design.R
import kotlinx.android.synthetic.main.ds_event_header.view.*

class DSEventHeader(context: Context, attrs: AttributeSet): FrameLayout(context, attrs) {
    var eventPoster: ImageView

    init {
        inflate(context, R.layout.ds_event_header, this)
        eventPoster = img_event_poster
    }

    fun setCloseAction(action: () -> Unit) {
        val listener = OnClickListener { action() }
        btn_back.setOnClickListener(listener)
    }

    fun setEvent(title: String) { txt_event_title.text = title }
}