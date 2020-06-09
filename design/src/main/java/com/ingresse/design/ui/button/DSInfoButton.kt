package com.ingresse.design.ui.button

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.ingresse.design.R
import com.ingresse.design.helper.ResourcesHelper
import com.ingresse.design.helper.setVisible
import kotlinx.android.synthetic.main.ds_info_button.view.*

class DSInfoButton(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {
    val resHelper = ResourcesHelper(context)

    init {
        inflate(context, R.layout.ds_info_button, this)
        val array = context.theme.obtainStyledAttributes(attrs, R.styleable.DSInfoButton, 0, 0)

        val title = array.getString(R.styleable.DSInfoButton_buttonTitle).orEmpty()
        val info = array.getString(R.styleable.DSInfoButton_buttonInfo).orEmpty()
        val infoImage = array.getDrawable(R.styleable.DSInfoButton_buttonImage)
        val showArrow = array.getBoolean(R.styleable.DSInfoButton_showArrow, true)

        setButton(title, info, infoImage, showArrow)
        array.recycle()
    }

    /**
     * Function to set the button action
     *
     * @param onClick - Callback for button click action
     */
    fun onButtonClick(onClick: (View) -> Unit) { layout_btn.setOnClickListener(onClick) }

    /**
     * Function to set the user information
     *
     * @param title - Button's title
     * @param info - Button's info. The label in left of arrow icon
     * @param infoImage - Button's image drawable. If null, the view will be hidden
     * @param showArrow - Variable to set if arrow icon is visible
     */
    fun setButton(title: String,
                  info: String,
                  infoImage: Drawable? = null,
                  showArrow: Boolean = true) {
        setMainProperties(title, info, showArrow)
        img_btn_info.setVisible(infoImage != null)
        img_btn_info.setImageDrawable(infoImage)
    }

    /**
     * Function to set the user information
     *
     * @param title - Button's title
     * @param info - Button's info. The label in left of arrow icon
     * @param infoImage - Button's image resource. If null, the view will be hidden
     * @param showArrow - Variable to set if arrow icon is visible
     */
    fun setButton(title: String,
                  info: String,
                  @DrawableRes infoImage: Int? = null,
                  showArrow: Boolean = true) {
        setMainProperties(title, info, showArrow)
        img_btn_info.setVisible(infoImage != null)

        val imageRes = infoImage ?: return
        val image = resHelper.getDrawableHelper(imageRes)
        img_btn_info.setImageDrawable(image)
    }

    private fun setMainProperties(title: String,
                                  info: String,
                                  showArrow: Boolean) {
        lbl_btn_title.text = title
        lbl_btn_info.text = info

        ic_arrow.setVisible(showArrow)
        if (showArrow) return
        val infoEndMargin = context.resources.getDimensionPixelSize(R.dimen.spacing_x4)
        val params = lbl_btn_info.layoutParams as MarginLayoutParams
        params.setMargins(params.leftMargin, params.topMargin, infoEndMargin, params.bottomMargin)
        lbl_btn_info.layoutParams = params
    }
}