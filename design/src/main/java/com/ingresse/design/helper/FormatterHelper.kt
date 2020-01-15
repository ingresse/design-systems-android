package com.ingresse.design.helper

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.ingresse.design.R
import com.ingresse.design.ui.editText.TextFormatType

class FormatText(val context: Context) {
    private var changing = false
    private var deleting = false
    private var previousLength = 0
    private var stringFormat = ""
    private var textWatcher: TextWatcher? = null

    fun mask(editText: EditText, format: TextFormatType) {
        stringFormat = when (format) {
            TextFormatType.NONE -> stringFormat
            TextFormatType.PHONE -> context.getString(R.string.format_phone_8_digits)
            TextFormatType.PHONE_9 -> context.getString(R.string.format_phone_9_digits)
            TextFormatType.INTERNATIONAL_PHONE -> context.getString(R.string.format_international_phone)
            TextFormatType.ZIPCODE -> context.getString(R.string.format_zipcode)
            TextFormatType.CPF -> context.getString(R.string.format_cpf)
            TextFormatType.CNPJ -> context.getString(R.string.format_cnpj)
            TextFormatType.MIXED_CPF_CNPJ -> context.getString(R.string.format_cpf)
            TextFormatType.CREDIT_CARD -> context.getString(R.string.format_credit_card)
            TextFormatType.SIMPLE_DATE -> context.getString(R.string.format_simple_date)
        }

        textWatcher = object : TextWatcherMin() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                deleting = count < before
            }

            override fun afterTextChanged(s: Editable?) {
                if (format == TextFormatType.NONE) return

                val currentText = s.unmask()

                if (changing || (deleting && currentText.length != format.minChar)) {
                    previousLength = currentText.length
                    changing = false
                    return
                }

                checkMutableFormat(format, currentText.length)
                if (previousLength == currentText.length) return

                changing = true
                val formattedText = reformatText(currentText)
                editText.setText(formattedText)
                editText.setSelection(formattedText.length)
            }
        }

        editText.addTextChangedListener(textWatcher)
    }

    fun removeTextFormat(editText: EditText) = editText.removeTextChangedListener(textWatcher)

    private fun reformatText(text: String): String {
        var formattedText = ""
        var textIndex = 0

        run loop@ {
            stringFormat.forEachIndexed { i, char ->
                if (textIndex >= text.length) return@loop

                formattedText += if (char != '#') char
                else text[textIndex]

                if (stringFormat[i] == '#') textIndex += 1
            }
        }

        return formattedText
    }

    private fun checkMutableFormat(currentFormat: TextFormatType, textSize: Int) {
        if (currentFormat == TextFormatType.PHONE) {
            stringFormat = if (textSize > currentFormat.minChar ?: 0) context.getString(R.string.format_phone_9_digits)
            else context.getString(R.string.format_phone_8_digits)
            return
        }

        if (currentFormat == TextFormatType.MIXED_CPF_CNPJ) {
            stringFormat = if (textSize > currentFormat.minChar ?: 0) context.getString(R.string.format_cnpj)
            else context.getString(R.string.format_cpf)
            return
        }
    }
}

fun String?.unmask(): String = this?.toCharArray()?.filter { it.isLetterOrDigit() }?.joinToString("").orEmpty()

fun String?.unmaskWithSpace(): String
        = this?.toCharArray()?.filter {
            it == ' ' || it.isLetterOrDigit()
        }?.joinToString("").orEmpty()

fun Editable?.unmask(): String = toString().unmask()