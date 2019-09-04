package com.ingresse.design.ui.image

import android.content.Context
import android.graphics.BitmapFactory
import android.util.AttributeSet
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.graphics.drawable.RoundedBitmapDrawable
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
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
        if (alphaIntensity != AlphaIntensity.ZERO) {
            val color = ResourcesHelper(context).getColorHelper(alphaIntensity.color)
            setColorFilter(color)
        }

        array.recycle()
    }

    fun setImage(url: String, key: String) {
        val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(smoothTransition).build()

        val glide = Glide.with(this)
                .load(url)
                .signature(ObjectKey(key))
                .transition(DrawableTransitionOptions().crossFade(factory))
                .diskCacheStrategy(DiskCacheStrategy.NONE)


        if (blurTransform != BlurIntensity.ZERO) glide.transform(blurTransform.blur)
        if (sizeTransform != ImageSize.ORIGINAL) glide.thumbnail(sizeTransform.multiplier)
        if (!roundImage) {
            glide.placeholder(placeholder).into(this)
            return
        }

        glide.placeholder(getRoundedPlaceholder()).transform(CircleCrop()).into(this)
    }

    private fun getRoundedPlaceholder(): RoundedBitmapDrawable {
        val bitmapRes = BitmapFactory.decodeResource(context.resources, placeholder)
        val circularBitmap = RoundedBitmapDrawableFactory.create(context.resources, bitmapRes)
        circularBitmap.isCircular = true

        return circularBitmap
    }
}