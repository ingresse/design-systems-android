package com.ingresse.design.ui.button

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.TextAppearanceSpan
import android.util.AttributeSet
import android.widget.LinearLayout
import com.ingresse.design.R
import com.ingresse.design.helper.animateBackground
import com.ingresse.design.helper.animateColor
import com.ingresse.design.helper.setVisible
import kotlinx.android.synthetic.main.ds_ticket_info.view.*
import java.text.MessageFormat

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

    init {
        inflate(context, R.layout.ds_ticket_info, this)
        updateStyle()
    }

    /**
     * Method to set view to 'passkey' mode
     *
     * @param passkey - passkey related to view
     */
    fun setPasskey(passkey: String) {
        this.passkey = passkey
        setType(TicketInfoType.PASSKEY)
    }

    /**
     * Method to set view to 'max' mode
     *
     * @param max - max limit to be showed on view
     */
    fun setMax(max: Int) {
        this.max = max
        setType(TicketInfoType.MAX)
    }

    /**
     * Method to set view to 'min' mode
     *
     * @param min - min limit to be showed on view
     */
    fun setMin(min: Int) {
        this.min = min
        setType(TicketInfoType.MIN)
    }

    /**
     * Method to set view to 'description' mode
     */
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
            TicketInfoType.MAX -> MessageFormat.format(resources.getString(R.string.ticket_info_max), max)
            TicketInfoType.MIN -> MessageFormat.format(resources.getString(R.string.ticket_info_min), min)
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

    private fun updateTextColor() = lbl_info.animateColor(getTextColor(), context)

    private fun updateBackgroundColor() {
        val backgroundRes = when(type) {
            TicketInfoType.MAX ->
                if (bottomRounded) R.drawable.ds_ticket_info_max_min_bottom_bg
                else R.color.tangerine_crystal
            TicketInfoType.MIN ->
                if (bottomRounded) R.drawable.ds_ticket_info_max_min_bottom_bg
                else R.color.tangerine_crystal
            TicketInfoType.PASSKEY ->
                if (bottomRounded) R.drawable.ds_ticket_info_passkey_bottom_bg
                else  R.color.mint_crystal
            TicketInfoType.DESCRIPTION ->
                if (bottomRounded) R.drawable.ds_ticket_info_description_bottom_bg
                else  R.color.ocean_crystal
        }

        layout_ds_ticket_info.animateBackground(backgroundRes, context)
    }

    private fun getTextColor(): Int =
        when (type) {
            TicketInfoType.MAX, TicketInfoType.MIN -> R.color.tangerine_dark
            TicketInfoType.PASSKEY -> R.color.mint_dark
            else -> R.color.ocean_dark
        }
}