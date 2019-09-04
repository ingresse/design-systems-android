package com.ingresse.design.ui.button

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.os.Build
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.ingresse.design.R
import kotlinx.android.synthetic.main.loader_button.view.*

class LoaderButton(context: Context, attrs: AttributeSet): FrameLayout(context, attrs) {
    private val text: String
    private val textColor: Int
    private val tintColor: Int
    private val horizontalPadding: Int

    private var loading: Boolean = false

    fun isLoading() = loading

    init {
        inflate(context, R.layout.loader_button, this)

        val defaultColor = ContextCompat.getColor(context, R.color.white)
        val defaultSize = context.resources.getDimensionPixelSize(R.dimen.spacing_3)
        val array = context.theme.obtainStyledAttributes(attrs, R.styleable.LoaderButton, 0, 0)
        text = array.getString(R.styleable.LoaderButton_text) ?: ""
        textColor = array.getColor(R.styleable.LoaderButton_textColor, defaultColor)
        tintColor = array.getColor(R.styleable.LoaderButton_tintColor, defaultColor)
        horizontalPadding = array.getDimensionPixelSize(R.styleable.LoaderButton_horizontalPadding, defaultSize)

        txt.text = text
        txt.setTextColor(textColor)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            progress.indeterminateTintList = ColorStateList.valueOf(tintColor)
        }

        frame.setPadding(horizontalPadding, 0, horizontalPadding, 0)

        array.recycle()
    }

    override fun setOnClickListener(l: OnClickListener?) {
        frame.setOnClickListener { if (!loading) l?.onClick(it) }
    }

    fun setLoading(state: Boolean) = if (state) startLoading() else stopLoading()

    fun startLoading() {
        val alpha = ValueAnimator.ofFloat(0F, 1F)
        alpha.duration = 200
        alpha.addUpdateListener {
            val value = it.animatedValue as? Float ?: return@addUpdateListener
            progress.alpha = value
            txt.alpha = 1F - value
        }
        alpha.addListener(object: Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationStart(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) {
                progress.alpha = 1F
                txt.alpha = 0F
            }
        })
        alpha.start()
        loading = true
    }

    fun stopLoading() {
        val alpha = ValueAnimator.ofFloat(1F, 0F)
        alpha.duration = 200
        alpha.addUpdateListener {
            val value = it.animatedValue as? Float ?: return@addUpdateListener
            progress.alpha = value
            txt.alpha = 1F - value
        }
        alpha.addListener(object: Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationStart(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) {
                progress.alpha = 0F
                txt.alpha = 1F
            }
        })
        alpha.start()
        loading = false
    }

    fun setText(@StringRes text: Int) = txt.setText(text)
}