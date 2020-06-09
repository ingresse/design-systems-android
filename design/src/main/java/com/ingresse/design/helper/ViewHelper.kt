package com.ingresse.design.helper

import android.content.Context
import android.content.res.Resources
import android.graphics.Rect
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.TouchDelegate
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DimenRes
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

fun View.setMargin(left: Int?, top: Int?, right: Int?, bottom: Int?) {
    val params = layoutParams as ViewGroup.MarginLayoutParams

    val leftDimen = left ?: params.leftMargin
    val rightDimen = right ?: params.rightMargin
    val topDimen = top ?: params.topMargin
    val bottomDimen = bottom ?: params.bottomMargin

    params.setMargins(leftDimen, topDimen, rightDimen, bottomDimen)
    layoutParams = params
}

fun View.setMarginByResources(context: Context,
                              @DimenRes left: Int?,
                              @DimenRes top: Int?,
                              @DimenRes right: Int?,
                              @DimenRes bottom: Int?) {
    val params = layoutParams as ViewGroup.MarginLayoutParams

    val leftDimen = if (left == null) params.leftMargin else
        context.resources.getDimensionPixelSize(left)

    val rightDimen = if (right == null) params.leftMargin else
        context.resources.getDimensionPixelSize(right)

    val topDimen = if (top == null) params.leftMargin else
        context.resources.getDimensionPixelSize(top)

    val bottomDimen = if (bottom == null) params.leftMargin else
        context.resources.getDimensionPixelSize(bottom)

    params.setMargins(leftDimen, topDimen, rightDimen, bottomDimen)
    layoutParams = params
}