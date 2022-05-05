package com.sn.secretive.ui.settings

import android.app.Activity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager

object SettingsUtils {

    const val SECRETIVE_THEME_MODE = "secretive.theme.mode"
    const val SECRETIVE_ABOUT = "secretive.about"

    const val SYSTEM = "System"
    private const val DARK = "Dark"
    private const val LIGHT = "Light"

    fun resolveThemeMode(activity: Activity?): String {
        return PreferenceManager.getDefaultSharedPreferences(activity)
            .getString(SECRETIVE_THEME_MODE, SYSTEM).toString()
    }

    fun changeTheme(theme: String) {
        when (theme) {
            SYSTEM -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
            DARK -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            LIGHT -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

}



