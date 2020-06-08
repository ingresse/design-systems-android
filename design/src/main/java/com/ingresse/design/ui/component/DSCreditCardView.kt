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
import com.ingresse.design.ui.editText.TextFormatType
import kotlinx.android.synthetic.main.ds_credit_card_view.view.*
import kotlinx.android.synthetic.main.ds_credit_card_view_back.view.*
import kotlinx.android.synthetic.main.ds_credit_card_view_front.view.*

class DSCreditCardView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {
    private var resHelper = ResourcesHelper(context)
    private var brand = MutableLiveData<CardBrands>()

    private var views: List<View>
    private var flipAnimation: FlipAnimation

    private val minCreditCardNumber = TextFormatType.CREDIT_CARD.minChar ?: 0

    var frontVisible = true
        get() = flipAnimation.frontIsVisible
        private set

    var cardNumber = ""
    set(value) {
        field = value
        lbl_credit_card_number.text = value.replace(".", " ")
        val unmaskedNumber = value.unmask()
        if (unmaskedNumber.length < minCreditCardNumber) return updateBrand(null)
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
        val gradientBottomColor = if (brand == null) R.color.desert_storm else R.color.mint_dark
        val gradientTopColor = if (brand == null) R.color.desert_storm else R.color.mint_light

        setBackground(gradientBottomColor, gradientTopColor)
        val iconRes = brand?.brandIcon ?: R.drawable.ic_empty_brand
        val brandImage = resHelper.getDrawableHelper(iconRes)
        img_credit_card_brand.setImageDrawable(brandImage)
    }

    private fun setBackground(@ColorRes bottomColor: Int, @ColorRes topColor: Int) {
        (layout_credit_card_front.background as GradientDrawable).apply {
            animateGradient(this,
                resHelper.getColorHelper(bottomColor),
                resHelper.getColorHelper(topColor))
        }

        (layout_credit_card_back.background as GradientDrawable).apply {
            animateGradient(this,
                resHelper.getColorHelper(bottomColor),
                resHelper.getColorHelper(topColor))
        }
    }

    fun flipCard() = flipAnimation.animateViews()

    fun showFrontCard() = flipAnimation.showFront()

    fun showBackCard() = flipAnimation.showBack()
}