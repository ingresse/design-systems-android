package com.ingresse.design.ui.button

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.widget.LinearLayout
import com.ingresse.design.R
import com.ingresse.design.helper.setVisible
import kotlinx.android.synthetic.main.ds_event_session_btn.view.*

class DSEventSessionButton(context: Context, attrs: AttributeSet): LinearLayout(context, attrs) {
    private var weekDay: String
    private var date: String
    private var hour: String
    private var enabled: Boolean
    private var selected: Boolean
    private var isPassport: Boolean
    private var passportName: String = resources.getString(R.string.passport)

    private val enabledColorRes: Int = R.color.ocean_light
    private val disabledColorRes: Int = R.color.mercury_10
    private val selectedColorRes: Int = R.color.white

    init {
        inflate(context, R.layout.ds_event_session_btn, this)

        val array = context.theme.obtainStyledAttributes(attrs, R.styleable.DSEventSessionButton, 0, 0)
        weekDay = array.getString(R.styleable.DSEventSessionButton_week_day) ?: ""
        date = array.getString(R.styleable.DSEventSessionButton_date) ?: ""
        hour = array.getString(R.styleable.DSEventSessionButton_hour) ?: ""
        enabled = array.getBoolean(R.styleable.DSEventSessionButton_enabled, true)
        selected = array.getBoolean(R.styleable.DSEventSessionButton_selected, false)
        isPassport = array.getBoolean(R.styleable.DSEventSessionButton_is_passport, false)

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

        val currentBackground = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) context.getDrawable(backgroundRes)
        else resources.getDrawable(backgroundRes)

        layout_ds_event_session.background = currentBackground
    }

    private fun getTextColor(): Int {
        val colorRes = when {
            !enabled -> disabledColorRes
            enabled && selected -> selectedColorRes
            else -> enabledColorRes
        }

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) context.getColor(colorRes)
        else resources.getColor(colorRes)
    }

    private fun updateTextColor() {
        val currentColor = getTextColor()

        lbl_passport_name.setTextColor(currentColor)
        lbl_passport_description.setTextColor(currentColor)

        lbl_week_day.setTextColor(currentColor)
        lbl_date.setTextColor(currentColor)
        lbl_hour.setTextColor(currentColor)
    }
}