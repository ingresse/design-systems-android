package com.ingresse.design.helper

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import com.google.android.material.snackbar.Snackbar
import com.ingresse.design.R
import kotlinx.android.synthetic.main.custom_snackbar_alert.view.*

enum class SnackbarColorSchema(@ColorRes val textColor: Int, @ColorRes val backgroundColor: Int) {
    LOGIN(R.color.ruby, R.color.white),
    DEFAULT(R.color.white, R.color.ruby)
}

class SnackbarHelper(private val viewGroup: ViewGroup?, context: Context?) {
    private val resHelper = ResourcesHelper(context)

    fun createCustomSnackbar(container: View,
                             title: String = "",
                             message: String,
                             shown: () -> Unit = {},
                             dismissed: () -> Unit = {},
                             duration: Int = Snackbar.LENGTH_LONG,
                             colorSchema: SnackbarColorSchema = SnackbarColorSchema.DEFAULT): Snackbar? {
        if (viewGroup == null) return null
        val snackbar = Snackbar.make(container, "", duration)
        val view = customView(title, message, colorSchema)
        snackbar.addCustomView(view)
        snackbar.addCallback(SnackbarCallback(shown, dismissed))
        return snackbar
    }

    private fun Snackbar.addCustomView(customView: View?) {
        val layout = view as Snackbar.SnackbarLayout
        layout.setPadding(0, 0, 0 ,0)
        layout.addView(customView)
    }

    private fun customView(title: String, message: String, colorSchema: SnackbarColorSchema): View? {
        val customSnackbar = viewGroup?.inflate(R.layout.custom_snackbar_alert)

        customSnackbar?.snackbar_title?.apply {
            setTextColor(resHelper.getColorHelper(colorSchema.textColor))
            text = title
            if (title.isEmpty()) visibility = View.GONE
        }

        customSnackbar?.snackbar_message?.apply {
            setTextColor(resHelper.getColorHelper(colorSchema.textColor))
            text = message
            if (message.isEmpty()) visibility = View.GONE
        }

        customSnackbar?.custom_snackbar_main?.setBackgroundColor(
                resHelper.getColorHelper(colorSchema.backgroundColor))

        return customSnackbar
    }
}

class SnackbarCallback(val shown: () -> Unit, val dismissed: () -> Unit) : Snackbar.Callback() {
    override fun onShown(sb: Snackbar?) {
        super.onShown(sb)
        shown()
    }

    override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
        super.onDismissed(transientBottomBar, event)
        if (event == DISMISS_EVENT_TIMEOUT) { dismissed() }
    }
}