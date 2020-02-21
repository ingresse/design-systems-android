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
    private var type: TicketInfoType
    private var passkey: String = "Passkey"
    private var max: Int = 0
    private var min: Int = 0

    init {
        inflate(context, R.layout.ds_ticket_info, this)
        val array = context.theme.obtainStyledAttributes(attrs, R.styleable.DSTicketInfo, 0, 0)
        val infoType = array.getInt(R.styleable.DSTicketInfo_ds_info_type, 0)
        type = TicketInfoType.fromId(infoType)
        updateStyle()
        array.recycle()
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
        lbl_info.text = when(type) {
            TicketInfoType.MAX -> resources.getQuantityString(R.plurals.ticket_info_max, max, max)
            TicketInfoType.MIN -> resources.getQuantityString(R.plurals.ticket_info_min, min, min)
            TicketInfoType.PASSKEY -> {
                val passkeyRes = context.getString(R.string.ticket_info_passkey)
                val passkeyAppearence = TextAppearanceSpan(context, R.style.DSTextStyle_TicketInfo_Passkey)
                val info = SpannableString(String.format(passkeyRes, passkey.toUpperCase()))
                if (passkey.isNotEmpty()) {
                    info.setSpan(passkeyAppearence, info.length - passkey.length, info.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
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
        val backgroundRes = when(type) {
            TicketInfoType.MAX -> R.color.tangerine_crystal
            TicketInfoType.MIN -> R.color.tangerine_crystal
            TicketInfoType.PASSKEY -> R.color.mint_crystal
            else -> R.color.ocean_crystal
        }

        val currentBackground = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) context.getDrawable(backgroundRes)
        else resources.getDrawable(backgroundRes)

        layout_ds_ticket_info.background = currentBackground
    }

    private fun getTextColor(): Int {
        val colorRes = when(type) {
            TicketInfoType.MAX -> R.color.tangerine_dark
            TicketInfoType.MIN -> R.color.tangerine_dark
            TicketInfoType.PASSKEY -> R.color.mint_dark
            else -> R.color.ocean_dark
        }

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) context.getColor(colorRes)
        else resources.getColor(colorRes)
    }
}