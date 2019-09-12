package com.ingresse.design.ui.user

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.ingresse.design.R
import com.ingresse.design.helper.USER_IMAGE_PREFIX
import kotlinx.android.synthetic.main.custom_profile_user_layout.view.*

class DSProfileUserData(context: Context, attrs: AttributeSet): FrameLayout(context, attrs) {
    init { inflate(context, R.layout.custom_profile_user_layout, this) }

    fun setUserData(name: String, email: String, picture: String) {
        txt_profile_user_name.text = name
        txt_profile_user_email.text = email
        img_profile_user.setImage(picture, USER_IMAGE_PREFIX + picture)
    }
}