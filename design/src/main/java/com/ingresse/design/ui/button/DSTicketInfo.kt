package com.ingresse.design.ui.button

import android.content.Context
import android.os.Build
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.TextAppearanceSpan
import android.util.AttributeSet
import android.widget.LinearLayout
import com.ingresse.design.R
import com.ingresse.design.helper.ResourcesHelper
import com.ingresse.design.helper.setVisible
import kotlinx.android.synthetic.main.ds_ticket_info.view.*

enum class TicketInfoType(val id: Int) {
    DESCRIPTION(0), MAX(1), MIN(2), PASSKEY(3);

    companion object {
        private val all = listOf(DESCRIPTION, MAX, MIN, PASSKEY)
        fun fromId(id: Int) = all.firstOrNull { it.id == id } ?: DESCRIPTION
    }
}

class DSTicketInfo(context: Context, attrs: AttributeSet): LinearLayout(context, attrs) {
    private var type: TicketInfoType = TicketInfoType.DESCRIPTION
    private var passkey: String = ""
    private var max: Int = 0
    private var min: Int = 0
    private var bottomRounded: Boolean = false

    private val resourcesHelper = ResourcesHelper(context)

    init {
        inflate(context, R.layout.ds_ticket_info, this)
        updateStyle()
    }

    fun setPasskey(passkey: String) {
        this.passkey = passkey
        setType(TicketInfoType.PASSKEY)
    }

    fun setMax(max: Int) {
        this.max = max
        setType(TicketInfoType.MAX)
    }

    fun setMin(min: Int) {
        this.min = min
        setType(TicketInfoType.MIN)
    }

    fun setDescription() = setType(TicketInfoType.DESCRIPTION)

    fun isBottomRounded(bottomRounded: Boolean) {
        this.bottomRounded = bottomRounded
        updateBackgroundColor()
    }

    private fun setType(type: TicketInfoType) {
        this.type = type
        updateStyle()
    }

    private fun updateStyle() {
        updateBackgroundColor()
        updateTextColor()
        updateIcon()
        updateText()
    }

    private fun updateIcon() {
        val typesWithIcon = listOf(TicketInfoType.DESCRIPTION, TicketInfoType.MAX)
        iv_info_icon.setVisible(typesWithIcon.contains(type))
        val icon = when (type) {
            TicketInfoType.MAX -> R.drawable.ic_warning
            else -> R.drawable.ic_info
        }
        iv_info_icon.setImageResource(icon)
    }

    private fun updateText() {
        lbl_info.text = when (type) {
            TicketInfoType.MAX -> resources.getQuantityString(R.plurals.ticket_info_max, max, max)
            TicketInfoType.MIN -> resources.getQuantityString(R.plurals.ticket_info_min, min, min)
            TicketInfoType.PASSKEY -> {
                val passkeyRes = context.getString(R.string.ticket_info_passkey)
                val passkeyAppearence =
                    TextAppearanceSpan(context, R.style.DSTextStyle_TicketInfo_Passkey)
                val info = SpannableString(String.format(passkeyRes, passkey.toUpperCase()))
                if (passkey.isNotEmpty()) {
                    info.setSpan(
                        passkeyAppearence,
                        info.length - passkey.length,
                        info.length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
                SpannableStringBuilder().append(info)
            }
            else -> context.getString(R.string.ticket_info_description)
        }
    }

    private fun updateTextColor() {
        val currentColor = getTextColor()
        lbl_info.setTextColor(currentColor)
    }

    private fun updateBackgroundColor() {
        val backgroundRes = when {
            type == TicketInfoType.MAX && bottomRounded -> R.drawable.ds_ticket_info_max_min_bottom_bg
            type == TicketInfoType.MIN && bottomRounded -> R.drawable.ds_ticket_info_max_min_bottom_bg
            type == TicketInfoType.PASSKEY && bottomRounded -> R.drawable.ds_ticket_info_passkey_bottom_bg
            type == TicketInfoType.DESCRIPTION && bottomRounded -> R.drawable.ds_ticket_info_description_bottom_bg
            type == TicketInfoType.MAX && !bottomRounded -> R.color.tangerine_crystal
            type == TicketInfoType.MIN && !bottomRounded -> R.color.tangerine_crystal
            type == TicketInfoType.PASSKEY && !bottomRounded -> R.color.mint_crystal
            else -> R.color.ocean_crystal
        }

        layout_ds_ticket_info.background = resourcesHelper.getDrawableHelper(backgroundRes)
    }

    private fun getTextColor(): Int =
        when (type) {
            TicketInfoType.MAX -> resourcesHelper.getColorHelper(R.color.tangerine_dark)
            TicketInfoType.MIN -> resourcesHelper.getColorHelper(R.color.tangerine_dark)
            TicketInfoType.PASSKEY -> resourcesHelper.getColorHelper(R.color.mint_dark)
            else -> resourcesHelper.getColorHelper(R.color.ocean_dark)
        }
}