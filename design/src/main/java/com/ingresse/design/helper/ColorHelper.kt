package com.ingresse.design.helper

import android.content.Context

private const val PREFERENCES_FILE = "color_configs"

private const val KEY_PRIMARY_COLOR = "primary_color"
private const val KEY_PRIMARY_50_COLOR = "primary_50_color"
private const val KEY_PRIMARY_DARK_COLOR = "primary_dark_color"
private const val KEY_SECONDARY_COLOR = "secondary_color"
private const val KEY_SECONDARY_DARK_COLOR = "secondary_dark_color"
private const val KEY_LINK_COLOR = "link_color"
private const val KEY_LINK_DARK_COLOR = "link_dark_color"

private const val DEFAULT_PRIMARY = "#00A5DB"
private const val DEFAULT_PRIMARY_50 = "#8000A5DB"
private const val DEFAULT_PRIMARY_DARK = "#008BC1"
private const val DEFAULT_SECONDARY = "#FCA311"
private const val DEFAULT_SECONDARY_DARK = "#FA8409"
private const val DEFAULT_LINK = "#FFF"
private const val DEFAULT_LINK_DARK = "#7FFFFFFF"

class ColorHelper(val context: Context?) {
    var primaryColor: String
        get() = getPreferences()?.getString(KEY_PRIMARY_COLOR, null) ?: DEFAULT_PRIMARY
        set(value) {
            val editor = getPreferences()?.edit() ?: return
            editor.putString(KEY_PRIMARY_COLOR, value)
            editor.apply()
        }

    var primary50Color: String
        get() = getPreferences()?.getString(KEY_PRIMARY_50_COLOR, null) ?: DEFAULT_PRIMARY_50
        set(value) {
            val editor = getPreferences()?.edit() ?: return
            editor.putString(KEY_PRIMARY_50_COLOR, value)
            editor.apply()
        }

    var primaryDarkColor: String
        get() = getPreferences()?.getString(KEY_PRIMARY_DARK_COLOR, null) ?: DEFAULT_PRIMARY_DARK
        set(value) {
            val editor = getPreferences()?.edit() ?: return
            editor.putString(KEY_PRIMARY_DARK_COLOR, value)
            editor.apply()
        }

    var secondaryColor: String
        get() = getPreferences()?.getString(KEY_SECONDARY_COLOR, null) ?: DEFAULT_SECONDARY
        set(value) {
            val editor = getPreferences()?.edit() ?: return
            editor.putString(KEY_SECONDARY_COLOR, value)
            editor.apply()
        }

    var secondaryDarkColor: String
        get() = getPreferences()?.getString(KEY_SECONDARY_DARK_COLOR, null) ?: DEFAULT_SECONDARY_DARK
        set(value) {
            val editor = getPreferences()?.edit() ?: return
            editor.putString(KEY_SECONDARY_DARK_COLOR, value)
            editor.apply()
        }

    var linkColor: String
        get() = getPreferences()?.getString(KEY_LINK_COLOR, null) ?: DEFAULT_LINK
        set(value) {
            val editor = getPreferences()?.edit() ?: return
            editor.putString(KEY_LINK_COLOR, value)
            editor.apply()
        }

    var linkDarkColor: String
        get() = getPreferences()?.getString(KEY_LINK_DARK_COLOR, null) ?: DEFAULT_LINK_DARK
        set(value) {
            val editor = getPreferences()?.edit() ?: return
            editor.putString(KEY_LINK_DARK_COLOR, value)
            editor.apply()
        }

    private fun getPreferences() = context?.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE)
}