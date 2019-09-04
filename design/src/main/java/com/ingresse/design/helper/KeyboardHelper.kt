package com.ingresse.design.helper

import android.content.Context
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager

object KeyboardHelper{
    private var inputMethod: InputMethodManager? = null

    @JvmStatic
    fun show(context: Context?, view: View? = null){
        view?.requestFocus()
        inputMethod = getInputMethod(context)
        inputMethod?.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

    @JvmStatic
    fun dismiss(context: Context?, view: View? = null){
        inputMethod = getInputMethod(context)
        inputMethod?.hideSoftInputFromWindow(view?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    private fun getInputMethod(context: Context?) : InputMethodManager? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context?.getSystemService(InputMethodManager::class.java)
        } else {
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        }
    }
}