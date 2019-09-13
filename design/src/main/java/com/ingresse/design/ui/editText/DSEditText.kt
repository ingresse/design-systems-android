package com.ingresse.design.ui.editText

import android.content.Context
import android.graphics.Typeface
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.text.method.SingleLineTransformationMethod
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.view.animation.TranslateAnimation
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.FrameLayout
import com.ingresse.design.R
import com.ingresse.design.helper.KeyboardHelper
import com.ingresse.design.helper.ResourcesHelper
import com.ingresse.design.helper.TextWatcherMin
import kotlinx.android.synthetic.main.ds_edit_text.view.*

class DSEditText(context: Context, attrs: AttributeSet): FrameLayout(context, attrs) {
    private val hint: String
    private val hintColor: Int
    private val textColor: Int
    private val isPassword: Boolean
    private val isLastField: Boolean
    private val showSuggestions: Boolean
    private val clearButton: Boolean
    private val capitalization: Capitalization

    private var passwordVisible: Boolean = false

    private val resHelper = ResourcesHelper(context)
    private var focusListener: (hasFocus: Boolean) -> Unit = {}

    val text: String get () = edit_text.text.toString()
    val editText: EditText get() = edit_text

    init {
        View.inflate(context, R.layout.ds_edit_text, this)

        val defaultColor = resHelper.getColorHelper(R.color.mercury)
        val array = context.theme.obtainStyledAttributes(attrs, R.styleable.DSEditText, 0, 0)
        hint = array.getString(R.styleable.DSEditText_hint) ?: ""
        hintColor = array.getColor(R.styleable.DSEditText_hintColor, defaultColor)
        val text = array.getString(R.styleable.DSEditText_text) ?: ""
        val action = array.getString(R.styleable.DSEditText_actionLabel) ?: ""
        textColor = array.getColor(R.styleable.DSEditText_textColor, defaultColor)
        isPassword = array.getBoolean(R.styleable.DSEditText_isPassword, false)
        isLastField = array.getBoolean(R.styleable.DSEditText_isLastField, false)
        val capsAttr = array.getInt(R.styleable.DSEditText_capitalization, 1)
        capitalization = Capitalization.fromId(capsAttr)
        showSuggestions = array.getBoolean(R.styleable.DSEditText_showSuggestion, true)
        clearButton = array.getBoolean(R.styleable.DSEditText_clearButton, false)

        if (isPassword) setPassword() else setTextType()
        if (isLastField) setLastField(action)
        if (capitalization != Capitalization.CAPITALIZED) setCapitalization()
        if (clearButton) setClearButton()

        edit_text.setText(text)
        edit_text.setTextColor(textColor)

        txt_hint.text = hint
        txt_hint.setTextColor(hintColor)
        setListeners()

        array.recycle()
    }

    private fun setLastField(action: String) {
        edit_text.imeOptions = EditorInfo.IME_ACTION_GO
        edit_text.setImeActionLabel(action, EditorInfo.IME_ACTION_GO)
    }

    private fun setCapitalization() {
        val filters = edit_text.filters
        val capFilter = InputFilter { source, _, _, _, _, _ -> when (capitalization) {
            Capitalization.LOWERCASE -> source.toString().toLowerCase()
            Capitalization.UPPERCASE -> source.toString().toUpperCase()
            else -> source
        }}
        edit_text.filters = filters.plus(capFilter)
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
        val textType = if (showSuggestions) InputType.TYPE_CLASS_TEXT else InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
        edit_text.inputType = textType or InputType.TYPE_TEXT_VARIATION_PASSWORD
    }

    private fun setClearButton() {
        val cancel = resHelper.getDrawableHelper(R.drawable.ic_clear_text)
        cancel.setBounds(0, -25, cancel.intrinsicWidth, cancel.intrinsicHeight - 25)

        val updateRightButton = {
            val drawable = if (edit_text.text.isEmpty()) null else cancel
            edit_text.setCompoundDrawables(null, null, drawable, null)
        }

        edit_text.setOnTouchListener { v, event ->
            when {
                v !is EditText -> false
                event.x < v.width - v.totalPaddingRight -> false
                event.action == MotionEvent.ACTION_UP -> {
                    edit_text.text.clear()
                    edit_text.setCompoundDrawables(null, null, null, null)
                    edit_text.requestFocus()
                    true
                }
                else -> true
            }
        }

        edit_text.addTextChangedListener(object: TextWatcherMin() {
            override fun afterTextChanged(s: Editable?) = updateRightButton()
        })
    }

    private fun setTextType() {
        edit_text.inputType = if (showSuggestions) InputType.TYPE_CLASS_TEXT else InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
    }

    private fun setListeners() {
        edit_text.setOnFocusChangeListener { v, hasFocus ->
            focusListener(hasFocus)
            animateTranslation(hasFocus)
            if (!hasFocus) KeyboardHelper.dismiss(context, edit_text)
        }
    }

    private fun animateTranslation(hasFocus: Boolean) {
        if (!edit_text.text.isNullOrEmpty()) return

        val movement = (txt_hint.height * -0.6).toFloat()
        val coords = if (hasFocus) Pair(0F, movement) else Pair(movement, 0F)

        val animation = TranslateAnimation(0F, 0F, coords.first, coords.second)
        animation.duration = 200
        animation.fillAfter = true
        txt_hint.startAnimation(animation)

        val fontSize = if (hasFocus) 14f else 16f
        txt_hint.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
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