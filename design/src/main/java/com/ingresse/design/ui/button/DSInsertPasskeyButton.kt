package com.ingresse.design.ui.button

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.ingresse.design.R
import com.ingresse.design.helper.ResourcesHelper
import com.ingresse.design.helper.animateBackground
import com.ingresse.design.helper.animateColor
import com.ingresse.design.helper.setVisible
import kotlinx.android.synthetic.main.btn_insert_passkey.view.*

class DSInsertPasskeyButton(context: Context, attrs: AttributeSet): LinearLayout(context, attrs) {
    private val resourcesHelper = ResourcesHelper(context)

    init {
        inflate(context, R.layout.btn_insert_passkey, this)
        updateState()
        updateTexts()
    }

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)
        updateState()
    }

    private fun updateState() {
        btn_change_passkey.setVisible(isSelected)
        updateTexts()
        updateComponentsColor()
        updateBackgroundColor()
    }

    fun setOnClick(listener: () -> Unit) = layout_add_passkey.setOnClickListener { listener() }

    fun setOnChangePasskeyClick(listener: () -> Unit) = btn_change_passkey.setOnClickListener { listener() }

    private fun updateTexts() {
        lbl_info.text = if (isSelected) "Ver ingressos" else "Adicionar código"
    }

    private fun updateBackgroundColor() {
        val backgroundRes = if (isSelected) R.drawable.ds_insert_passkey_btn_selected_bg
        else R.drawable.ds_insert_passkey_btn_bg

        layout_add_passkey.animateBackground(backgroundRes, context)
    }

    private fun updateComponentsColor() {
        val colorRes = if (isSelected) R.color.white else R.color.mercury_30
        val ticketRes = if (isSelected) R.drawable.icon_ticket_fill else R.drawable.icon_ticket_stroke
        lbl_info.animateColor(colorRes, context)

        iv_ticket.setImageDrawable(resourcesHelper.getDrawableHelper(ticketRes))
        iv_ticket.setColorFilter(ContextCompat.getColor(context, colorRes), android.graphics.PorterDuff.Mode.SRC_IN)
        iv_arrow.setColorFilter(ContextCompat.getColor(context, colorRes), android.graphics.PorterDuff.Mode.SRC_IN)
    }
}