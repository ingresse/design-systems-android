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

        animateSnack(context.resources.getDimension(R.dimen.spacing_x4),
            1.0f,
            0,
            300) {
            animateSnack(0.0f, 0.0f, VISIBLE_DURATION, 300)
        }
    }

    private fun animateSnack(yPosition: Float,
                             alpha: Float,
                             delay: Long,
                             duration: Long,
                             onEnd: (() -> Unit) = {}) {
        animate()
            .translationY(yPosition)
            .alpha(alpha)
            .setDuration(duration)
            .setStartDelay(delay)
            .withEndAction { onEnd() }
    }
}