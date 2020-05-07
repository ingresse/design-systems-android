package com.ingresse.design.ui.dialog

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.ingresse.design.R
import com.ingresse.design.helper.ResourcesHelper
import com.ingresse.design.helper.hide
import com.ingresse.design.helper.show
import kotlinx.android.synthetic.main.ds_top_snackbar.view.*
import java.util.*
import kotlin.concurrent.schedule

class DSTopSnackbar(context: Context, attrs: AttributeSet): RelativeLayout(context, attrs) {
    val resHelper = ResourcesHelper(context)

    var positiveIcon = resHelper.getDrawableHelper(R.drawable.ic_check_bamboo)
    var negativeIcon = resHelper.getDrawableHelper(R.drawable.ic_cross_ruby)

    fun showPositiveMessage(message: String) {
        lbl_top_snackbar.text = message
        lbl_top_snackbar.setTextColor(resHelper.getColorHelper(R.color.bamboo))
        iv_top_snackbar.setImageDrawable(positiveIcon)
        showSnack()
    }

    fun showNegativeMessage(message: String) {
        lbl_top_snackbar.text = message
        lbl_top_snackbar.setTextColor(resHelper.getColorHelper(R.color.ruby))
        iv_top_snackbar.setImageDrawable(negativeIcon)
        showSnack()
    }

    private fun showSnack() {
        card_snackbar.show()
        Timer("Dismiss", false).schedule(1000) {
            card_snackbar.hide()
        }
    }
}