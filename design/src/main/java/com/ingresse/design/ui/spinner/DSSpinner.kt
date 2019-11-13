package com.ingresse.design.ui.spinner

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.animation.TranslateAnimation
import android.widget.*
import com.ingresse.design.R
import com.ingresse.design.helper.ResourcesHelper
import kotlinx.android.synthetic.main.custom_spinner.view.*
import kotlinx.android.synthetic.main.custom_spinner.view.txt_hint

class DSSpinner(context: Context, attrs: AttributeSet): FrameLayout(context, attrs) {
    private val hint: String
    private val hintColor: Int
    private var customHints: List<String> = emptyList()

    private val resHelper = ResourcesHelper(context)
    private var hasFirstItem = false
    var isWrong = false

    private val textView: TextView get() = txt_hint
    val spinner: Spinner get() = custom_spinner

    init {
        inflate(context, R.layout.custom_spinner, this)

        val defaultColor = resHelper.getColorHelper(R.color.mercury)
        val array = context.theme.obtainStyledAttributes(attrs, R.styleable.DSSpinner, 0, 0)
        hint = array.getString(R.styleable.DSSpinner_customHint) ?: ""
        hintColor = array.getColor(R.styleable.DSSpinner_customHintColor, defaultColor)

        txt_hint.text = hint
        txt_hint.setTextColor(hintColor)
        setListeners()
    }

    fun setListeners(onItemSelected: ((position: Int) -> Unit)? = null) {
        val listener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (customHints.size > position) txt_hint.text = customHints[position]
                if (position == 0) animateHintToCenter() else animateTranslation(position)
                onItemSelected?.invoke(position)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        spinner.onItemSelectedListener = listener
    }

    private fun animateTranslation(position: Int) {
        val movement = (textView.height * -0.6).toFloat()
        val coords = if (hasFirstItem && position == 0) Pair(movement, 0F) else Pair(0F, movement)
        val animation = TranslateAnimation(0F, 0F, coords.first, coords.second)
        animation.duration = 200
        animation.fillAfter = true
        textView.startAnimation(animation)

        val fontSize = if (hasFirstItem && position == 0) 16f else 14f
        txt_hint.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
    }

    private fun animateHintToCenter() {
        val movement = (textView.height * -0.6).toFloat()
        val animation = TranslateAnimation(0F, 0F, movement, 0F)
        animation.duration = 200
        animation.fillAfter = true
        textView.startAnimation(animation)
        txt_hint.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
    }

    fun setHint(newHint: String) { txt_hint.text = newHint }

    fun setItems(items: List<String>, shortItems: List<String> = emptyList(), hints: List<String> = emptyList(), firstItem: String? = null) {
        val spinnerAdapter = spinner.adapter as? DSSpinnerAdapter ?: createAdapter()
        if (spinner.adapter == null) spinner.adapter = spinnerAdapter

        hasFirstItem = (firstItem != null)
        customHints = hints
        spinnerAdapter.putItemAsFirst(firstItem)
        spinnerAdapter.items = items
        spinnerAdapter.shortItems = shortItems
        spinnerAdapter.notifyDataSetChanged()
    }

    private fun DSSpinnerAdapter.putItemAsFirst(item: String?) { if (hasFirstItem) this.insert(item, 0) }

    private fun createAdapter() = DSSpinnerAdapter(context)

    fun setSpinnerHintTextDefault() {
        txt_hint.setTextColor(hintColor)
        isWrong = false
    }

    fun setSpinnerHintTextError(animation: Boolean = false) {
        val errorColor = resHelper.getColorHelper(R.color.ruby)
        txt_hint.setTextColor(errorColor)
        if(animation) animateHintToCenter()
        isWrong = true
    }

    fun validateEmptyEditTextError() {
        if (isWrong)  {
            animateHintToCenter()
            setSpinnerHintTextError()
            return
        }
        setSpinnerHintTextDefault()
    }
}