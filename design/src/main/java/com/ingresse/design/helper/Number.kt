package com.ingresse.design.helper

import java.text.NumberFormat

fun Double.toCurrency(): String {
    val formatter = NumberFormat.getCurrencyInstance()
    return formatter.format(this)
}