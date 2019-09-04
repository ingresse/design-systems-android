package com.ingresse.design.ui.tab

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import com.google.android.material.tabs.TabLayout
import com.ingresse.design.helper.ColorHelper

class ThemedTab(context: Context, attrs: AttributeSet): TabLayout(context, attrs) {
    private val colorHelper = ColorHelper(context)

    init { setupColor() }

    private fun setupColor() {
        val primary = try { Color.parseColor(colorHelper.primaryColor) } catch (ignored: Exception) { Color.BLUE }
        val primary50 = try { Color.parseColor(colorHelper.primary50Color) } catch (ignored: Exception) { primary }
        setSelectedTabIndicatorColor(primary)
        setTabTextColors(primary50, primary)
    }
}