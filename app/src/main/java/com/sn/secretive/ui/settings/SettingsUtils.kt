package com.sn.secretive.ui.settings

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager

object SettingsUtils {

    const val SECRETIVE_REPOSITORY_LINK = "https://github.com/emreesen27/Secretive"

    const val SECRETIVE_THEME_MODE = "secretive.theme.mode"
    const val SECRETIVE_ABOUT = "secretive.about"
    const val SECRETIVE_CHANGE_PIN = "secretive.change.pin"

    const val SYSTEM = "System"
    private const val DARK = "Dark"
    private const val LIGHT = "Light"

    fun resolveThemeMode(context: Context): String {
        return PreferenceManager.getDefaultSharedPreferences(context)
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
