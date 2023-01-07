package com.sn.secretive.ui.settings

import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.sn.secretive.R

class SettingsFragment : PreferenceFragmentCompat() {

    private lateinit var mListenerOptions: OnSharedPreferenceChangeListener

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        val aboutButton: Preference? = findPreference(SettingsUtils.SECRETIVE_ABOUT)
        aboutButton?.setOnPreferenceClickListener {
            findNavController().navigate(SettingsFragmentDirections.actionSettingsToAbout())
            true
        }

        val themeListPreference: ListPreference? =
            findPreference(SettingsUtils.SECRETIVE_THEME_MODE)

        mListenerOptions =
            OnSharedPreferenceChangeListener { _: SharedPreferences?, key: String ->
                when (key) {
                    SettingsUtils.SECRETIVE_THEME_MODE -> {
                        SettingsUtils.changeTheme(
                            themeListPreference?.value ?: SettingsUtils.SYSTEM
                        )
                    }
                }
            }
    }

    override fun onPause() {
        super.onPause()
        preferenceManager.sharedPreferences?.unregisterOnSharedPreferenceChangeListener(
            mListenerOptions
        )
    }

    override fun onResume() {
        super.onResume()
        preferenceManager.sharedPreferences?.registerOnSharedPreferenceChangeListener(
            mListenerOptions
        )
    }
}
