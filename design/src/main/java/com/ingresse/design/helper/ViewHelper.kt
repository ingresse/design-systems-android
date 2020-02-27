package com.ingresse.design.helper

import android.content.res.Resources
import android.graphics.Rect
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.TouchDelegate
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun View.setVisible(condition: Boolean = true) {
    visibility = if (condition) View.VISIBLE else View.GONE
}

fun View.setInvisible(condition: Boolean = true) {
    visibility = if (condition) View.INVISIBLE else View.VISIBLE
}

fun View.setViewEnabled(condition: Boolean = true) {
    isEnabled = condition
    alpha = if (condition) 1.0f else 0.6f
}

// Functions to increase view hit area
fun View.increaseHitArea(dp: Float) {
    // increase the hit area
    val increasedArea = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().displayMetrics).toInt()
    val parent = parent as View
    parent.post {
        val rect = Rect()
        getHitRect(rect)
        rect.top -= increasedArea
        rect.left -= increasedArea
        rect.bottom += increasedArea
        rect.right += increasedArea
        parent.touchDelegate = TouchDelegate(rect, this)
    }
}

fun View.increaseHitArea(top: Float, bottom: Float, left: Float, right: Float) {
    // increase the hit area
    val topIncrease = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, top, Resources.getSystem().displayMetrics).toInt()
    val bottomIncrease = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, bottom, Resources.getSystem().displayMetrics).toInt()
    val leftIncrease = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, left, Resources.getSystem().displayMetrics).toInt()
    val rightIncrease = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, right, Resources.getSystem().displayMetrics).toInt()

    val parent = parent as View
    parent.post {
        val rect = Rect()
        getHitRect(rect)
        rect.top -= topIncrease
        rect.left -= leftIncrease
        rect.bottom += bottomIncrease
        rect.right += rightIncrease
        parent.touchDelegate = TouchDelegate(rect, this)
    }
}
