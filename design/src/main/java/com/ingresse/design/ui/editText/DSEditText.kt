package com.ingresse.design.ui.editText

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.text.method.SingleLineTransformationMethod
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.TranslateAnimation
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.ingresse.design.R
import com.ingresse.design.helper.*
import kotlinx.android.synthetic.main.custom_edit_text.view.*

class DSEditText(context: Context, attrs: AttributeSet): FrameLayout(context, attrs) {
    private val hint: String
    private val hintColor: Int
    private val textColor: Int
    private val isPassword: Boolean
    private val isLastField: Boolean
    private val showSuggestions: Boolean
    private val clearButton: Boolean
    private val capitalization: Capitalization
    private val uppercaseHint: Boolean
    private val textInputType: TextInputType
    private val textFormatType: TextFormatType
    private val editColor: Int
    private val defaultColor: Int
    private var errorDisabled: Boolean = false
    private var passwordVisible: Boolean = false

    var isWrong = false

    private val resHelper = ResourcesHelper(context)
    private var focusListener: (hasFocus: Boolean) -> Unit = {}

    val text: String get () = edit_text.text.toString()
    val editText: EditText get() = edit_text

    init {
        inflate(context, R.layout.custom_edit_text, this)

        defaultColor = Color.parseColor(ColorHelper(context).primaryColor)
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
        uppercaseHint = array.getBoolean(R.styleable.DSEditText_uppercaseHint, false)
        val inputType = array.getInt(R.styleable.DSEditText_textInputType, 0)
        textInputType = TextInputType.fromId(inputType)
        val formatType = array.getInt(R.styleable.DSEditText_formatType, 0)
        textFormatType = TextFormatType.fromId(formatType)
        val customStyle = array.getResourceId(R.styleable.DSEditText_customStyle, 0)
        editColor = array.getColor(R.styleable.DSEditText_editColor, defaultColor)
        errorDisabled = array.getBoolean(R.styleable.DSEditText_disableError, false)

        if (isPassword) setPassword()
        if (isLastField) setLastField(action)
        if (capitalization != Capitalization.CAPITALIZED) setCapitalization()
        if (textInputType != TextInputType.NONE) setInputType()
        if (clearButton) setClearButton()
        if (customStyle != 0) resHelper.setTextAppearanceHelper(editText, customStyle)

        editText.setHandleColor(editColor)
        editText.setCursorColor(editColor)

        setFormatType()

        layout.hint = if (uppercaseHint) hint.toUpperCase() else hint
        edit_text.setText(text)
        edit_text.setTextColor(textColor)
        edit_text.setHintTextColor(hintColor)

        setFocusListener()
        array.recycle()
    }

    private fun setFormatType() {
         if (textFormatType == TextFormatType.NONE) return
        FormatText(context).mask(editText, textFormatType)
    }

    private fun setInputType() { editText.inputType = textInputType.type }

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

    fun setFocusChangeListener(listener: (hasFocus: Boolean) -> Unit) { focusListener = listener }

    private fun setFocusListener() {
        val errorColor = resHelper.getColorHelper(R.color.ruby)

        editText.setOnFocusChangeListener listener@{ v, hasFocus ->
            focusListener(hasFocus)
            animateTranslation(v, hasFocus)
            if (!hasFocus) KeyboardHelper.dismiss(context, edit_text)

            // WRONG AND SELECTED COLORS

            if (hasFocus) {
                edit_text.setTextColor(textColor)
                layout.defaultHintTextColor = ColorStateList.valueOf(defaultColor)
                return@listener
            }

            if ((editText.text.toString().count() == 0
                    || editText.text.toString().count() < textFormatType.minCharFormatted ?: 0)
                    && !errorDisabled) {
                edit_text.setTextColor(errorColor)
                layout.defaultHintTextColor = ColorStateList.valueOf(errorColor)
                isWrong = true
                return@listener
            }

            edit_text.setTextColor(textColor)
            layout.defaultHintTextColor = ColorStateList.valueOf(hintColor)
            isWrong = false
        }
    }

    private fun EditText.setHandleColor(@ColorInt color: Int) {
        try {
            val selectHandle = TextView::class.java.getDeclaredField("mTextSelectHandleRes")
            val textEditor = TextView::class.java.getDeclaredField("mEditor")

            selectHandle.isAccessible = true
            textEditor.isAccessible = true

            val selectHandleDrawable = selectHandle.getInt(this)
            val editor = textEditor.get(this)

            val drawable = ContextCompat.getDrawable(this.context, selectHandleDrawable)!!
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN)

            val centerHandle = editor.javaClass.getDeclaredField("mSelectHandleCenter")
            val leftHandle = editor.javaClass.getDeclaredField("mSelectHandleLeft")
            val rightHandle = editor.javaClass.getDeclaredField("mSelectHandleRight")

            centerHandle.isAccessible = true
            leftHandle.isAccessible = true
            rightHandle.isAccessible = true

            centerHandle.set(editor, drawable)
            leftHandle.set(editor, drawable)
            rightHandle.set(editor, drawable)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun EditText.setCursorColor(@ColorInt color: Int) {
        try {
            val textCursor = TextView::class.java.getDeclaredField("mCursorDrawableRes")
            val textEditor = TextView::class.java.getDeclaredField("mEditor")

            textCursor.isAccessible = true
            textEditor.isAccessible = true

            val cursorDrawable = textCursor.getInt(this)
            val editor = textEditor.get(this)

            val drawable = ContextCompat.getDrawable(this.context, cursorDrawable)
            drawable?.setColorFilter(color, PorterDuff.Mode.SRC_IN)
            val drawables = arrayOf(drawable, drawable)

            val textCursorDrawable = editor.javaClass.getDeclaredField("mCursorDrawable")
            textCursorDrawable.isAccessible = true
            textCursorDrawable.set(editor, drawables)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}