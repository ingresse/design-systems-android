package com.ingresse.design.ui.spinner

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.TranslateAnimation
import android.widget.*
import com.ingresse.design.R
import com.ingresse.design.helper.ResourcesHelper
import kotlinx.android.synthetic.main.custom_spinner.view.*

class CustomSpinner(context: Context, attrs: AttributeSet): FrameLayout(context, attrs) {
    private val hint: String
    private val hintColor: Int

    private val resHelper = ResourcesHelper(context)
    private var hasFirstItem = false

    private val textView: TextView get() = txt_hint
    val spinner: Spinner get() = custom_spinner

    init {
        inflate(context, R.layout.custom_spinner, this)

        val defaultColor = resHelper.getColorHelper(R.color.mercury)
        val array = context.theme.obtainStyledAttributes(attrs, R.styleable.CustomSpinner, 0, 0)
        hint = array.getString(R.styleable.CustomSpinner_customHint) ?: ""
        hintColor = array.getColor(R.styleable.CustomSpinner_customHintColor, defaultColor)

        txt_hint.text = hint
        txt_hint.setTextColor(hintColor)
        setListeners()
    }

    fun setListeners(onItemSelected: ((position: Int, item: Any?) -> Unit)? = null) {
        val listener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                animateTranslation(position)
                onItemSelected?.invoke(position, parent?.adapter?.getItem(position))
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        spinner.onItemSelectedListener = listener
    }

    private fun animateTranslation(position: Int) {
        val movement = (textView.height * -0.6).toFloat()
        val coords = if (hasFirstItem  && position == 0) Pair(movement, 0F) else Pair(0F, movement)
        val animation = TranslateAnimation(0F, 0F, coords.first, coords.second)
        animation.duration = 200
        animation.fillAfter = true
        textView.startAnimation(animation)
    }

    fun <T> setItems(items: List<T>, firstItem: T? = null) {
        val spinnerAdapter: ArrayAdapter<T> = spinner.adapter as? ArrayAdapter<T> ?: createAdapter()
        if (spinner.adapter == null) spinner.adapter = spinnerAdapter

        hasFirstItem = (firstItem != null)
        spinnerAdapter.putItemAsFirst(firstItem)
        spinnerAdapter.addAll(items)
        spinnerAdapter.notifyDataSetChanged()
    }

    private fun <T> ArrayAdapter<T>.putItemAsFirst(item: T?) { if (hasFirstItem) this.insert(item, 0) }

    private fun <T> createAdapter() = ArrayAdapter<T>(context, R.layout.item_custom_spinner, R.id.txt_item)
}