package com.ingresse.design.helper

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.PorterDuff
import android.graphics.Rect
import android.os.Build
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.TouchDelegate
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DimenRes
import androidx.annotation.LayoutRes
import androidx.core.view.ViewCompat

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

/**
 * Method to increase hit box around view
 *
 * @param dp - how many dp will be increase around view
 */
fun View.increaseHitArea(dp: Float) {
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

/**
 * Method to increase hit box around view
 *
 * @param top - how many dp will be increase top of view
 * @param bottom - how many dp will be increase bottom of view
 * @param left - how many dp will be increase left of view
 * @param right - how many dp will be increase right of view
 */
fun View.increaseHitArea(top: Float, bottom: Float, left: Float, right: Float) {
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

fun View.setMargin(left: Int? = null,
                   top: Int? = null,
                   right: Int? = null,
                   bottom: Int? = null) {
    val params = layoutParams as ViewGroup.MarginLayoutParams

    val leftDimen = left ?: params.leftMargin
    val rightDimen = right ?: params.rightMargin
    val topDimen = top ?: params.topMargin
    val bottomDimen = bottom ?: params.bottomMargin

    params.setMargins(leftDimen, topDimen, rightDimen, bottomDimen)
    layoutParams = params
}

fun View.setMarginByResources(context: Context,
                              @DimenRes left: Int? = null,
                              @DimenRes top: Int? = null,
                              @DimenRes right: Int? = null,
                              @DimenRes bottom: Int? = null) {

    val resHelper = ResourcesHelper(context)
    val params = layoutParams as ViewGroup.MarginLayoutParams

    val leftDimen = resHelper.getNullableDimen(left) ?: params.leftMargin
    val rightDimen = resHelper.getNullableDimen(right) ?: params.rightMargin
    val topDimen = resHelper.getNullableDimen(top) ?: params.topMargin
    val bottomDimen = resHelper.getNullableDimen(bottom) ?: params.bottomMargin

    params.setMargins(leftDimen, topDimen, rightDimen, bottomDimen)
    layoutParams = params
}

fun View.setTintList(colorsList: ColorStateList) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) backgroundTintList = colorsList
    else ViewCompat.setBackgroundTintList(this, colorsList)
}

fun View.setTintMode(mode: PorterDuff.Mode) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) backgroundTintMode = mode
    else ViewCompat.setBackgroundTintMode(this, mode)
}