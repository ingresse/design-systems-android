package com.ingresse.design.ui.button

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.ingresse.design.R
import kotlinx.android.synthetic.main.ds_unit_controller.view.*

class DSUnitController(context: Context, attrs: AttributeSet): LinearLayout(context, attrs) {
    private var count: Int = 0
    var plusStep: Int = 1
    var minusStep: Int = 1

    init {
        inflate(context, R.layout.ds_unit_controller, this)
        updateCount()
        setupButtons()
    }

    private fun setupButtons() {
        btn_plus.setOnClickListener { plus(plusStep) }
        btn_minus.setOnClickListener { minus(minusStep) }
    }

    fun plus(units: Int) {
        count+=units
        updateCount()
    }

    fun minus(units: Int) {
        count-=units
        updateCount()
    }

    private fun updateCount() { lbl_count.text = count.toString() }
}