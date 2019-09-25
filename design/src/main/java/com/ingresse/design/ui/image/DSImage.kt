package com.ingresse.design.ui.image

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.bumptech.glide.signature.ObjectKey
import com.ingresse.design.R
import com.ingresse.design.helper.ResourcesHelper

class DSImage(context: Context, attrs: AttributeSet): AppCompatImageView(context, attrs) {
    private var sizeTransform: ImageSize
    private var blurTransform: BlurIntensity
    private var roundImage: Boolean
    private var smoothTransition: Boolean
    private var resHelper = ResourcesHelper(context)

    @DrawableRes private var placeholder: Int

    init {
        val array = context.theme.obtainStyledAttributes(attrs, R.styleable.DSImage, 0, 0)
        val imageSize = array.getInt(R.styleable.DSImage_imageSize, 0)
        val blurIntensity = array.getInt(R.styleable.DSImage_blurIntensity, 0)

        sizeTransform = ImageSize.fromId(imageSize)
        blurTransform = BlurIntensity.fromId(blurIntensity)
        roundImage = array.getBoolean(R.styleable.DSImage_roundImage, false)
        smoothTransition = array.getBoolean(R.styleable.DSImage_smoothTransition, true)
        placeholder = array.getResourceId(R.styleable.DSImage_placeholder, R.drawable.default_poster_white)

        val alpha = array.getInt(R.styleable.DSImage_alphaIntensity, 0)
        val alphaIntensity = AlphaIntensity.findId(alpha)
        setAlpha(alphaIntensity)

        array.recycle()
    }

    fun setImage(image: String, key: String) {
        val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(smoothTransition).build()

        val imageToLoad: Any = if (image.isNotEmpty()) image else resHelper.getDrawableHelper(placeholder)

        val glide = Glide.with(this)
                .load(imageToLoad)
                .signature(ObjectKey(key))
                .transition(DrawableTransitionOptions().crossFade(factory))
                .diskCacheStrategy(DiskCacheStrategy.DATA)

        if (blurTransform != BlurIntensity.ZERO) glide.transform(blurTransform.blur)
        if (sizeTransform != ImageSize.ORIGINAL) glide.thumbnail(sizeTransform.multiplier)
        if (!roundImage) {
            glide.placeholder(placeholder).into(this)
            return
        }

        glide.placeholder(placeholder).transform(CircleCrop()).into(this)
    }

    fun setAlpha(value: AlphaIntensity) {
        if (value == AlphaIntensity.ZERO) return
        val color = resHelper.getColorHelper(value.color)
        setColorFilter(color)

        if (!roundImage) return

        val gradientDrawable = GradientDrawable()
        gradientDrawable.shape = GradientDrawable.OVAL
        gradientDrawable.setColor(color)

        background = gradientDrawable
    }
}