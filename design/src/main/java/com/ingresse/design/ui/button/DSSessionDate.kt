package com.ingresse.design.ui.button

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.widget.LinearLayout
import com.ingresse.design.R
import com.ingresse.design.helper.ResourcesHelper
import kotlinx.android.synthetic.main.ds_session_date.view.*

class DSSessionDate(context: Context, attrs: AttributeSet): LinearLayout(context, attrs) {
    private var weekDay: String
    private var datetime: String
    private var selected: Boolean

    private val resourcesHelper = ResourcesHelper(context)

    init {
        inflate(context, R.layout.ds_session_date, this)
        val array = context.theme.obtainStyledAttributes(attrs, R.styleable.DSSessionDate, 0, 0)
        weekDay = array.getString(R.styleable.DSSessionDate_week_day) ?: ""
        datetime = array.getString(R.styleable.DSSessionDate_datetime) ?: ""
        selected = array.getBoolean(R.styleable.DSSessionDate_selected, false)

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

        val dateTimeBGRes = if (selected) R.drawable.ds_session_date_selected_bg else R.drawable.ds_session_date_bg

        layout_ds_session_date.background = resourcesHelper.getDrawableHelper(dateTimeBGRes)
        lbl_week_day.background = resourcesHelper.getDrawableHelper(weekDayBGRes)
    }


    private fun updateTextColor() {
        val weekDayColorRes = if (selected) R.color.ocean else R.color.white
        val dateTimeColorRes = if (selected) R.color.ocean else R.color.mercury_50

        lbl_week_day.setTextColor(resourcesHelper.getColorHelper(weekDayColorRes))
        lbl_datetime.setTextColor(resourcesHelper.getColorHelper(dateTimeColorRes))
    }
}