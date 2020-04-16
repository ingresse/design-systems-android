package com.ingresse.design.ui.header

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.ingresse.design.R
import com.ingresse.design.helper.setVisible
import kotlinx.android.synthetic.main.custom_header.view.*
import kotlinx.android.synthetic.main.ds_locale_header.view.*
import kotlinx.android.synthetic.main.ds_locale_header.view.btn_back

class DSLocaleHeader(context: Context, attrs: AttributeSet): FrameLayout(context, attrs) {
    init {
        inflate(context, R.layout.ds_locale_header, this)

        val array = context.theme.obtainStyledAttributes(attrs, R.styleable.DSLocaleHeader, 0, 0)
        val returnType = array.getBoolean(R.styleable.DSLocaleHeader_returnButtonEnabled, false)

        if (returnType) btn_back.visibility = View.VISIBLE
    }

    fun setHeader(category: String? = null, place: String, onPlaceClick: () -> Unit) {
        txt_place.text = place
        txt_place.setOnClickListener { onPlaceClick() }
        txt_category.setVisible(!category.isNullOrEmpty())
        txt_category.text = category
    }

    fun updatePlace(place: String) { txt_place.text = place }

    fun setCloseAction(action: () -> Unit) { btn_back.setOnClickListener { action() } }
}