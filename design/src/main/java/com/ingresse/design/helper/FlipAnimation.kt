package com.ingresse.design.helper

import android.animation.AnimatorSet
import android.content.Context
import android.view.View
import com.ingresse.design.R

const val CAMERA_DISTANCE = 20000

class FlipAnimation(private val context: Context, views: List<View>) {
    private var views = setCameraDistance(views)
    private var animations = setFlipAnimation()
    var frontIsVisible = true
        private set

    private fun setCameraDistance(views: List<View>): List<View> {
        val scale = context.resources.displayMetrics.density * CAMERA_DISTANCE
        return views.map {
            it.cameraDistance = scale
            return@map it
        }
    }

    private fun setFlipAnimation(): List<AnimatorSet> {
        val animators = mutableListOf<AnimatorSet>()
        animators.addAllAnimations(context, listOf(
                R.animator.flip_regular_out,
                R.animator.flip_regular_in,
                R.animator.flip_reverse_out,
                R.animator.flip_reverse_in
            )
        )
        return animators
    }

    fun animateViews() {
        if (frontIsVisible) return showBack()
        showFront()
    }

    fun showFront() {
        animations[2].setTarget(views[1])
        animations[3].setTarget(views[0])
        animations[2].start()
        animations[3].start()

        frontIsVisible = true
    }

    fun showBack() {
        animations[0].setTarget(views[0])
        animations[1].setTarget(views[1])
        animations[0].start()
        animations[1].start()

        frontIsVisible = false
    }
}