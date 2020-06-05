package com.ingresse.design.ui.component

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.ColorRes
import androidx.lifecycle.MutableLiveData
import com.ingresse.design.R
import com.ingresse.design.helper.FlipAnimation
import com.ingresse.design.helper.ResourcesHelper
import com.ingresse.design.helper.animateGradient
import com.ingresse.design.helper.unmask
import kotlinx.android.synthetic.main.ds_credit_card_view.view.*
import kotlinx.android.synthetic.main.ds_credit_card_view_back.view.*
import kotlinx.android.synthetic.main.ds_credit_card_view_front.view.*

class DSCreditCardView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {
    private var resHelper = ResourcesHelper(context)
    private var brand = MutableLiveData<CardBrands>()

    private var views: List<View>
    private var flipAnimation: FlipAnimation

    var frontVisible = true

    var cardNumber = ""
    set(value) {
        field = value
        lbl_credit_card_number.text = value.replace(".", " ")
        val unmaskedNumber = value.unmask()
        if (unmaskedNumber.length < 10) return updateBrand(null)
        val brand = CardBrands.findByRegex(unmaskedNumber)
        updateBrand(brand)
    }

    var holderName = ""
    set(value) {
        field = value
        lbl_credit_card_name.text = value
    }

    var expirationDate = ""
    set(value) {
        field = value
        lbl_credit_card_expiration_date.text = value
    }

    var cvv = ""
    set(value) {
        field = value
        lbl_credit_card_cvv.text = cvv
    }

    init {
        inflate(context, R.layout.ds_credit_card_view, this)
        views = listOf(front_view, back_view)
        flipAnimation =  FlipAnimation(context, views)
        updateBrand(null)
    }

    private fun updateBrand(brand: CardBrands?) {
        val colorOneRes = if (brand == null) R.color.desert_storm else R.color.mint_dark
        val colorTwoRes = if (brand == null) R.color.desert_storm else R.color.mint_light

        setBackground(colorOneRes, colorTwoRes)
        val iconRes = brand?.brandIcon ?: R.drawable.ic_empty_brand
        val brandImage = resHelper.getDrawableHelper(iconRes)
        img_credit_card_brand.setImageDrawable(brandImage)
    }

    private fun setBackground(@ColorRes resOne: Int, @ColorRes resTwo: Int) {
        (layout_credit_card_front.background as GradientDrawable).apply {
            animateGradient(this,
                resHelper.getColorHelper(resOne),
                resHelper.getColorHelper(resTwo))
        }

        (layout_credit_card_back.background as GradientDrawable).apply {
            animateGradient(this,
                resHelper.getColorHelper(resOne),
                resHelper.getColorHelper(resTwo))
        }
    }

    fun flipCard() {
        flipAnimation.animateViews()
        frontVisible = !frontVisible
    }

    fun showFrontCard() {
        flipAnimation.showFront()
        frontVisible = true
    }

    fun showBackCard() {
        flipAnimation.showBack()
        frontVisible = false
    }
}