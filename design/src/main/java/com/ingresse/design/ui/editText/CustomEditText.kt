package com.ingresse.design.ui.editText

import android.content.Context
import android.graphics.Typeface
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.text.method.SingleLineTransformationMethod
import android.util.AttributeSet
import android.view.View
import android.view.animation.TranslateAnimation
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.ingresse.design.R
import kotlinx.android.synthetic.main.custom_edit_text.view.*

class CustomEditText(context: Context, attrs: AttributeSet): FrameLayout(context, attrs) {
    private val hint: String
    private val hintColor: Int
    private val textColor: Int
    private val isPassword: Boolean
    private val isLastField: Boolean

    private var passwordVisible: Boolean = false

    private var focusListener: (hasFocus: Boolean) -> Unit = {}

    val text: String get () = edit_text.text.toString()
    val editText: EditText get() = edit_text

    init {
        inflate(context, R.layout.custom_edit_text, this)

        val defaultColor = ContextCompat.getColor(context, android.R.color.black)
        val array = context.theme.obtainStyledAttributes(attrs, R.styleable.CustomEditText, 0, 0)
        hint = array.getString(R.styleable.CustomEditText_hint) ?: ""
        hintColor = array.getColor(R.styleable.CustomEditText_hintColor, defaultColor)
        val text = array.getString(R.styleable.CustomEditText_text) ?: ""
        val action = array.getString(R.styleable.CustomEditText_actionLabel) ?: ""
        textColor = array.getColor(R.styleable.CustomEditText_textColor, defaultColor)
        isPassword = array.getBoolean(R.styleable.CustomEditText_isPassword, false)
        isLastField = array.getBoolean(R.styleable.CustomEditText_isLastField, false)

        if (isPassword) setPassword()
        if (isLastField) setLastField(action)

        layout.hint = hint
        edit_text.setText(text)
        edit_text.setTextColor(textColor)
        edit_text.setHintTextColor(hintColor)
        edit_text.setOnFocusChangeListener { v, hasFocus ->
            focusListener(hasFocus)
            animateTranslation(v, hasFocus)
        }

        array.recycle()
    }

    private fun setLastField(action: String) {
        edit_text.imeOptions = EditorInfo.IME_ACTION_GO
        edit_text.setImeActionLabel(action, EditorInfo.IME_ACTION_GO)
    }

    private fun setPassword() {
        btn_pass.visibility = View.VISIBLE
        btn_pass.setOnClickListener {
            passwordVisible = !passwordVisible
            val method = if (passwordVisible) SingleLineTransformationMethod() else PasswordTransformationMethod()
            edit_text.transformationMethod = method
            btn_pass.isSelected = passwordVisible
            edit_text.setSelection(edit_text.text.length)
            if (passwordVisible) edit_text.typeface = Typeface.SANS_SERIF
        }
        edit_text.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
    }

    private fun animateTranslation(v: View, up: Boolean) {
        val edt = v as? EditText ?: return
        if (edt.text.isNotEmpty()) return
        val movement = (v.height * 0.2).toFloat()
        val start = if (up) 0F else movement
        val end = if (up) movement else 0F
        val animation = TranslateAnimation(0F, 0F, start, end)
        animation.duration = 200
        animation.fillAfter = true
        edt.startAnimation(animation)
    }

    fun setActionListener(listener: () -> Unit) {
        edit_text.setOnEditorActionListener { _, actionId, _ ->
            if (actionId != EditorInfo.IME_ACTION_GO) return@setOnEditorActionListener false
            listener()
            return@setOnEditorActionListener true
        }
    }

    fun setFocusChangeListener(listener: (hasFocus: Boolean) -> Unit) {
        focusListener = listener
    }
}