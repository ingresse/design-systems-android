package com.ingresse.design.ui.image

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.ingresse.design.R
import kotlinx.android.synthetic.main.custom_profile_image.view.*

class DSProfileImage(context: Context, attrs: AttributeSet): FrameLayout(context, attrs) {
    private val userImage: DSImage get() = img_profile_user

    init {
        inflate(context, R.layout.custom_profile_image, this)

        val array = context.theme.obtainStyledAttributes(attrs, R.styleable.DSProfileImage, 0, 0)
        val alpha = array.getInt(R.styleable.DSProfileImage_alphaIntensity, 0)
        val alphaIntensity = AlphaIntensity.findId(alpha)
        userImage.setAlpha(alphaIntensity)

        if (array.getBoolean(R.styleable.DSProfileImage_imageAbove, false)) {
            val customImage = array.getResourceId(R.styleable.DSProfileImage_customImage, 0)
            if (customImage != 0) img_above.setImageResource(customImage)
            else img_above.setImageResource(R.drawable.ic_photo)
        }

        array.recycle()
    }

    fun setImage(image: String, key: String) = userImage.setImage(image, key)
}