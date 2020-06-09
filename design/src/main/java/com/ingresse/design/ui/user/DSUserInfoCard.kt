package com.ingresse.design.ui.user

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.ingresse.design.R
import com.ingresse.design.helper.ResourcesHelper
import com.ingresse.design.helper.USER_IMAGE_PREFIX
import com.ingresse.design.helper.setVisible
import kotlinx.android.synthetic.main.ds_user_info_card.view.*

class DSUserInfoCard(context: Context, attrs: AttributeSet): ConstraintLayout(context, attrs) {
    val resHelper = ResourcesHelper(context)
    var type: DSUserInfoCardType = DSUserInfoCardType.UNDEFINED
    set(value) {
        field = value
        updateButton()
    }

    init {
        inflate(context, R.layout.ds_user_info_card, this)
        val array = context.theme.obtainStyledAttributes(attrs, R.styleable.DSUserInfosCard, 0, 0)

        val typeId = array.getInt(R.styleable.DSUserInfosCard_userCardType,
            DSUserInfoCardType.UNDEFINED.id)

        type = DSUserInfoCardType.getById(typeId)
        updateButton()

        array.recycle()
    }

    /**
     * Function to set the button action
     *
     * @param onClick - Callback for button click action
     */
    fun onButtonClick(onClick: (View) -> Unit) { btn_action.setOnClickListener(onClick) }

    /**
     * Function to set the user information
     *
     * @param name - User's name. If is empty, the label will be hidden.
     * @param email - User's email. If is empty, the label will be hidden.
     * @param address - User's address. If is empty, the label will be hidden.
     * @param picture - User's picture.
     */
    fun setUserData(name: String = "",
                    email: String = "",
                    address: String = "",
                    picture: String) {

        lbl_user_name.text = name
        lbl_user_email.text = email
        lbl_user_address.text = address
        img_user_profile.setImage(picture, USER_IMAGE_PREFIX + picture)

        lbl_user_name.setVisible(name.isNotEmpty())
        lbl_user_email.setVisible(email.isNotEmpty())
        lbl_user_address.setVisible(address.isNotEmpty())
    }

    private fun updateButton() {
        val btnIcon = type.icon ?: return btn_action.setImageDrawable(null)
        val icon = resHelper.getDrawableHelper(btnIcon)
        btn_action.setImageDrawable(icon)
    }
}