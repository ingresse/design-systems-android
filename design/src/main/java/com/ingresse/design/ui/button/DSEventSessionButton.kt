package com.ingresse.design.ui.button

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.ingresse.design.R
import com.ingresse.design.helper.animateBackground
import com.ingresse.design.helper.animateColor
import com.ingresse.design.helper.setVisible
import kotlinx.android.synthetic.main.ds_event_session_btn.view.*

class DSEventSessionButton(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {
    private var weekDay: String = ""
    private var date: String = ""
    private var hour: String = ""
    private var enabled: Boolean = true
    private var selected: Boolean = false
    private var isPassport: Boolean = false
    private var passportName: String = resources.getString(R.string.passport)

    private val enabledColorRes: Int = R.color.ocean_light
    private val disabledColorRes: Int = R.color.mercury_10
    private val selectedColorRes: Int = R.color.white

    init {
        inflate(context, R.layout.ds_event_session_btn, this)
        updateLayout()
        updateBackgroundColor()
        updateTextColor()
        updateTexts()
    }

    fun setWeekDay(weekDay: String) {
        this.weekDay = weekDay
        updateTexts()
    }

    fun setDate(date: String) {
        this.date = date
        updateTexts()
    }

    fun setHour(hour: String) {
        this.hour = hour
        updateTexts()
    }

    fun setPassportName(passportName: String) {
        this.passportName = passportName
        updateTexts()
    }

    fun setSessionDate(weekDay: String, date: String, hour: String) {
        this.weekDay = weekDay
        this.date = date
        this.hour = hour
        isPassport(false)
        updateTexts()
    }

    fun setSessionPassport(passportName: String) {
        setPassportName(passportName)
        isPassport(true)
    }

    override fun setSelected(isSelected: Boolean) {
        super.setSelected(isSelected)
        selected = isSelected
        updateState()
    }

    override fun setEnabled(isEnabled: Boolean) {
        super.setEnabled(isEnabled)
        enabled = isEnabled
        updateState()
    }

    fun isPassport(isPassport: Boolean) {
        this.isPassport = isPassport
        updateLayout()
    }

    fun setOnClick(listener: () -> Unit) = setOnClickListener { listener() }

    private fun updateState() {
        updateTextColor()
        updateBackgroundColor()
    }

    private fun updateTexts() {
        lbl_passport_name.text = passportName
        lbl_week_day.text = weekDay
        lbl_date.text = date
        lbl_hour.text = hour
    }

    private fun updateLayout() {
        lbl_passport_name.setVisible(isPassport)
        lbl_passport_description.setVisible(isPassport)
        lbl_week_day.setVisible(!isPassport)
        lbl_date.setVisible(!isPassport)
        lbl_hour.setVisible(!isPassport)
    }

    private fun updateBackgroundColor() {
        val backgroundRes = when {
            !enabled -> R.drawable.ds_event_session_disabled_bg
            enabled && selected -> R.drawable.ds_event_session_selected_bg
            else -> R.drawable.ds_event_session_enabled_bg
        }

        layout_ds_event_session.animateBackground(backgroundRes, context)
    }

    private fun getTextColor(): Int =
        when {
            !enabled -> disabledColorRes
            enabled && selected -> selectedColorRes
            else -> enabledColorRes
        }

    private fun updateTextColor() {
        val currentColor = getTextColor()

        lbl_passport_name.animateColor(currentColor, context)
        lbl_passport_description.animateColor(currentColor, context)

        lbl_week_day.animateColor(currentColor, context)
        lbl_date.animateColor(currentColor, context)
        lbl_hour.animateColor(currentColor, context)
    }
}