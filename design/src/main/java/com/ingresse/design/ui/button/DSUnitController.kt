package com.ingresse.design.ui.button

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.ingresse.design.R
import com.ingresse.design.helper.increaseHitArea
import kotlinx.android.synthetic.main.ds_unit_controller.view.*

class DSUnitController(context: Context, attrs: AttributeSet): LinearLayout(context, attrs) {
    var count: Int = 0
    var plusStep: Int = 1
    var minusStep: Int = 1

    init {
        inflate(context, R.layout.ds_unit_controller, this)
        updateCount()
        setupButtons()

        btn_plus.increaseHitArea(12f)
        btn_minus.increaseHitArea(12f)
    }

    private fun setupButtons() {
        setOnPlusClickListener()
        setOnMinusClickListener()
    }

    fun setOnPlusClickListener(listener: (() -> Unit)? = null) {
        btn_plus.setOnClickListener { listener?.invoke() ?: plus() }
    }

    fun setOnMinusClickListener(listener: (() -> Unit)? = null) {
        btn_minus.setOnClickListener { listener?.invoke() ?: minus() }
    }

    fun plus() {
        count += plusStep
        updateCount()
    }

    fun minus() {
        count -= minusStep
        updateCount()
    }

    private fun updateCount() { lbl_count.text = count.toString() }
}