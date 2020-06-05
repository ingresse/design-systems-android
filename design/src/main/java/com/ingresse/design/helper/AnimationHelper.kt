package com.ingresse.design.helper

import android.animation.*
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.TransitionDrawable
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.TextView
import androidx.annotation.AnimatorRes
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.animation.doOnEnd
import androidx.core.view.isVisible

fun TextView.animateColor(@ColorRes newColor: Int,
                          context: Context,
                          duration: Long = 100) {
    val color = ResourcesHelper(context).getColorHelper(newColor)
    val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), this.textColors.defaultColor, color)
    colorAnimation.addUpdateListener { animator -> this.setTextColor(animator.animatedValue as Int) }
    colorAnimation.duration = duration
    colorAnimation.start()
}

fun View.changeBackground(@DrawableRes newBackground: Int,
                          context: Context,
                          withAnimation: Boolean = true,
                          duration: Int = 100) {
    if (withAnimation) return animateBackground(newBackground, context, duration)
    background = ResourcesHelper(context).getDrawableHelper(newBackground)
}

fun View.animateBackground(@DrawableRes newBackground: Int,
                           context: Context,
                           duration: Int = 100) {
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

fun createAnimation(from: Float,
                    to: Float,
                    duration: Long,
                    listener: ((ValueAnimator) -> Unit),
                    onEnd: (() -> Unit)? = null) {
    val animator = ValueAnimator.ofFloat(from, to)
    animator.duration = duration
    animator.addUpdateListener(listener)
    animator.doOnEnd { onEnd?.invoke() }
    animator.start()
}

fun TextView.animateValue(from: Double,
                          to: Double,
                          listener: ((Float)->Unit),
                          onEnd: (() -> Unit)? = null,
                          duration: Long = 1000) {
    createAnimation(from.toFloat(), to.toFloat(), duration, {
        val value = it.animatedValue as Float
        listener(value)
    }, onEnd = onEnd)
}

fun animateGradient(gradientBackground: GradientDrawable, start: Int, end: Int, duration: Long = 500) {
    val evaluator = ArgbEvaluator()
    val animator = TimeAnimator.ofFloat(0.0f, 1.0f)
    animator.duration = duration
    animator.addUpdateListener {
        val fraction = it.animatedFraction
        val newStart = evaluator.evaluate(fraction, end, start) as Int
        val newEnd = evaluator.evaluate(fraction, start, end) as Int

        gradientBackground.colors = intArrayOf(newStart, newEnd)
    }
    animator.start()
}

fun View.hide(duration: Long = 300) {
    if (!isVisible) return
    val animation = AlphaAnimation(alpha, 0.0f)
    animation.duration = duration
    animation.setListeners(doOnEnd = { setVisible(false) })
    startAnimation(animation)
}

fun View.show(duration: Long = 300) {
    if (isVisible) return
    val animation = AlphaAnimation(0.0f, 1.0f)
    animation.duration = duration
    animation.setListeners(doOnStart = { setVisible(true) })
    startAnimation(animation)
}

fun Animation.setListeners(doOnStart: (() -> Unit)? = null,
                           doOnEnd: (() -> Unit)? = null,
                           doOnRepeat: (() -> Unit)? = null) {
    setAnimationListener(object: Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation?) { doOnStart?.invoke() }
        override fun onAnimationEnd(animation: Animation?) { doOnEnd?.invoke() }
        override fun onAnimationRepeat(animation: Animation?) { doOnRepeat?.invoke() }
    })
}

fun MutableList<AnimatorSet>.addAnimation(context: Context, @AnimatorRes animator: Int) =
    add(AnimatorInflater.loadAnimator(context, animator) as AnimatorSet)

fun MutableList<AnimatorSet>.addAllAnimations(
    context: Context,
    @AnimatorRes animators: List<Int>) =
    animators.forEach { addAnimation (context, it) }