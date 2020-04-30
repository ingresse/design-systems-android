package com.ingresse.design.helper

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.drawable.TransitionDrawable
import android.text.format.DateUtils
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.animation.doOnEnd

fun TextView.animateColor(@ColorRes newColor: Int, context: Context, duration: Long = 100) {
    val color = ResourcesHelper(context).getColorHelper(newColor)
    val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), this.textColors.defaultColor, color)
    colorAnimation.addUpdateListener { animator -> this.setTextColor(animator.animatedValue as Int) }
    colorAnimation.duration = duration
    colorAnimation.start()
}

fun View.animateBackground(@DrawableRes newBackground: Int, context: Context, duration: Int = 100) {
    if (background == null) {
        background = ResourcesHelper(context).getDrawableHelper(newBackground)
        return
    }

    val backgrounds = arrayOf(background, ResourcesHelper(context).getDrawableHelper(newBackground))
    val crossfader = TransitionDrawable(backgrounds)

    background = crossfader
    crossfader.isCrossFadeEnabled = true
    crossfader.startTransition(duration)
}

fun createAnimation(from: Float, to: Float, duration: Long, listener: ((ValueAnimator) -> Unit), onEnd: (() -> Unit)? = null) {
    val animator = ValueAnimator.ofFloat(from, to)
    animator.duration = duration
    animator.addUpdateListener(listener)
    animator.doOnEnd { onEnd?.invoke() }
    animator.start()
}

fun TextView.animateValue(from: Double, to: Double, listener: ((Float)->Unit), onEnd: (() -> Unit)? = null, duration: Long = 1000) {
    createAnimation(from.toFloat(), to.toFloat(), duration, {
        val value = it.animatedValue as Float
        listener(value)
    }, onEnd = onEnd)
}