package com.ingresse.design.ui.button

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.ingresse.design.R
import com.ingresse.design.helper.animateBackground
import com.ingresse.design.helper.animateColor
import kotlinx.android.synthetic.main.ds_session_date.view.*

class DSSessionDate(context: Context, attrs: AttributeSet): LinearLayout(context, attrs) {
    private var weekDay: String = ""
    private var datetime: String = ""
    private var selected: Boolean = false

    init {
        inflate(context, R.layout.ds_session_date, this)
        updateState()
        updateTexts()
    }

    fun setWeekDay(weekDay: String) {
        this.weekDay = weekDay
        updateTexts()
    }

    fun setDatetime(datetime: String) {
        this.datetime = datetime
        updateTexts()
    }

    /**
     * Method to set properties to SessionDate
     *
     * @param weekDay - week day from date
     * @param datetime - view date and time to be showed
     */
    fun setProperties(weekDay: String, datetime: String) {
        this.weekDay = weekDay
        this.datetime = datetime
        updateTexts()
    }

    override fun setSelected(isSelected: Boolean) {
        selected = isSelected
        updateState()
    }

    private fun updateState() {
        updateTextColor()
        updateBackgroundColor()
    }

    private fun updateTexts() {
        lbl_week_day.text = weekDay
        lbl_datetime.text = datetime
    }

    private fun updateBackgroundColor() {
        val weekDayBGRes = if (selected) R.drawable.ds_session_date_weekday_selected_bg
        else R.drawable.ds_session_date_weekday_bg

        val dateTimeBGRes = if (selected) R.drawable.ds_session_date_selected_bg
        else R.drawable.ds_session_date_bg

        layout_ds_session_date.animateBackground(dateTimeBGRes, context)
        lbl_week_day.animateBackground(weekDayBGRes, context)
    }

    private fun updateTextColor() {
        val weekDayColorRes = if (selected) R.color.ocean else R.color.white
        val dateTimeColorRes = if (selected) R.color.ocean else R.color.mercury_50
        lbl_week_day.animateColor(weekDayColorRes, context)
        lbl_datetime.animateColor(dateTimeColorRes, context)
    }
}