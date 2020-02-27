package com.ingresse.design.helper

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.drawable.TransitionDrawable
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes

fun TextView.animateColor(@ColorRes newColor: Int, context: Context, duration: Long = 200) {
    val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), this.textColors.defaultColor, ResourcesHelper(context).getColorHelper(newColor))
    colorAnimation.addUpdateListener { animator -> this.setTextColor(animator.animatedValue as Int) }
    colorAnimation.duration = duration
    colorAnimation.start()
}

fun View.animateBackground(@DrawableRes newBackground: Int, context: Context, duration: Int = 200) {
    if (background == null) {
        background = ResourcesHelper(context).getDrawableHelper(newBackground)
        return
    }

    val backgrounds = arrayOf(background, ResourcesHelper(context).getDrawableHelper(newBackground))
    val crossfader = TransitionDrawable(backgrounds)

    background = crossfader
    crossfader.startTransition(duration)
}