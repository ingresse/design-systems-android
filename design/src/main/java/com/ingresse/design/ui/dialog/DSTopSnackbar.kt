package com.ingresse.design.ui.dialog

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.ingresse.design.R
import com.ingresse.design.helper.ResourcesHelper
import com.ingresse.design.helper.setVisible
import kotlinx.android.synthetic.main.ds_top_snackbar.view.*

const val VISIBLE_DURATION: Long = 1500

class DSTopSnackbar(context: Context, attrs: AttributeSet): RelativeLayout(context, attrs) {
    val resHelper = ResourcesHelper(context)

    var positiveIcon = resHelper.getDrawableHelper(R.drawable.ic_check_bamboo)
    var negativeIcon = resHelper.getDrawableHelper(R.drawable.ic_cross_ruby)

    init { inflate(context, R.layout.ds_top_snackbar, this) }

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
        setVisible()

        animate()
            .translationY(context.resources.getDimension(R.dimen.spacing_x4))
            .alpha(1.0f)
            .setDuration(300)
            .setStartDelay(0)
            .withEndAction {

            animate()
                .translationY(0.0f)
                .alpha(0.0f)
                .setDuration(300)
                .setStartDelay(VISIBLE_DURATION)
                .start()
        }.start()
    }
}