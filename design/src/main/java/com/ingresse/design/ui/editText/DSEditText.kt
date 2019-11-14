package com.ingresse.design.ui.editText

import android.content.Context
import android.graphics.Color
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
import com.ingresse.design.helper.*
import kotlinx.android.synthetic.main.ds_edit_text.view.*

class DSEditText(context: Context, attrs: AttributeSet): FrameLayout(context, attrs) {
    private var hint: String
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
    private var passwordVisible: Boolean = false
    private var hasNext: Boolean = false

    var originalTranslationY = 0F
    var isWrong = false
    var errorDisabled: Boolean = false

    private val resHelper = ResourcesHelper(context)
    private var focusListener: (hasFocus: Boolean) -> Unit = {}

    val text: String get () = edit_text.text.toString()
    val editText: EditText get() = edit_text

    init {
        View.inflate(context, R.layout.ds_edit_text, this)

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
        val nextFocus = attrs.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "nextFocusDown", -1)
        hasNext = nextFocus != -1

        if (isPassword) setPassword()
        if (isLastField) setLastField(action)
        if (capitalization != Capitalization.CAPITALIZED) setCapitalization()
        if (textInputType != TextInputType.NONE) setInputType()
        if (clearButton) setClearButton()
        if (customStyle != 0) resHelper.setTextAppearanceHelper(editText, customStyle)

        edit_text.setText(text)
        edit_text.setTextColor(textColor)

        txt_hint.text = if (uppercaseHint) hint.toUpperCase() else hint
        txt_hint.setTextColor(hintColor)

        originalTranslationY = editText.translationY

        setFocusListener()
        setFormatType()
        array.recycle()
    }

    fun setHint(text: String) {
        hint = text
        txt_hint.text = if (uppercaseHint) hint.toUpperCase() else hint
    }

    fun clearText() {
        editText.setText("")
        editText.setSelection(0)
    }

    fun setTextDS(txt: String?, cleanWhenEmpty: Boolean = false, wrongWhenEmpty: Boolean = false) {
        if (cleanWhenEmpty && txt.isNullOrEmpty()) {
            editText.text.clear()
            if (!editText.hasFocus()) animateHintToCenter()
        }

        if (wrongWhenEmpty && txt.isNullOrEmpty()) setEditTextError()

        if (txt.isNullOrEmpty()) return
        editText.setText(txt)
        editText.setSelection(txt.length)
        setEditTextDefault()
        animateHintToTop()
    }

    fun getTextDS(): String = editText.text.toString()

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

    private fun setListeners() {
        edit_text.setOnFocusChangeListener { v, hasFocus ->
            focusListener(hasFocus)
            animateTranslation(hasFocus)
            if (!hasFocus && !hasNext) KeyboardHelper.dismiss(context, edit_text)
        }
    }

    private fun animateHintToTop() {
        val movement = resHelper.resources.getDimension(R.dimen.height_text_view_normal) * - 0.6F
        val animation = TranslateAnimation(0F, 0F, 0F, movement)
        animation.duration = 200
        animation.fillAfter = true
        txt_hint.startAnimation(animation)

        val fontSize = 14f
        txt_hint.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
    }

    private fun animateHintToCenter() {
        val movement = resHelper.resources.getDimension(R.dimen.height_text_view_normal) * - 0.6F
        val animation = TranslateAnimation(0F, 0F, movement, 0F)
        animation.duration = 200
        animation.fillAfter = true
        txt_hint.startAnimation(animation)

        val fontSize = 16f
        txt_hint.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
    }

    private fun animateTranslation(hasFocus: Boolean) {
        if (!edit_text.text.isNullOrEmpty()) return
        if (hasFocus) animateHintToTop() else animateHintToCenter()
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
        editText.setOnFocusChangeListener listener@{ v, hasFocus ->
            focusListener(hasFocus)
            animateTranslation(hasFocus)
            if (!hasFocus && !hasNext) KeyboardHelper.dismiss(context, edit_text)

            // Set isWrong and hint with different color when focused

            if (hasFocus) return@listener setEditTextDefault()

            val textCount = editText.text.toString().count()
            val minCount = if (textFormatType == TextFormatType.MIXED_CPF_CNPJ
                    && textCount > textFormatType.minCharFormatted ?: 0) textFormatType.maxCharFormatted
            else textFormatType.minCharFormatted

            if (textInputType == TextInputType.EMAIL && !editText.text.toString().isValidEmail()) {
                setEditTextError()
                return@listener
            }

            if (textFormatType == TextFormatType.MIXED_CPF_CNPJ
                    && textCount < minCount ?: 0 && textCount > 0) {
                setEditTextError()
                return@listener
            }

            if ((textCount < textFormatType.minCharFormatted ?: 0
                    || textCount == 0) && !errorDisabled) {
                setEditTextError()
                return@listener
            }

            setEditTextDefault()
        }
    }

    private fun setEditTextError() {
        val errorColor = resHelper.getColorHelper(R.color.ruby)
        edit_text.setTextColor(errorColor)
        txt_hint.setTextColor(errorColor)
        isWrong = true
    }

    private fun setEditTextDefault() {
        edit_text.setTextColor(textColor)
        txt_hint.setTextColor(hintColor)
        isWrong = false
    }

    fun validateEmptyEditTextError() {
        if(edit_text.text.isEmpty()) { setEditTextError() }
        else { setEditTextDefault() }
    }
}