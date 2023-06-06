package com.cryptocurrency.core

import android.content.Context

/**
 * Created by Zavodov on 06.06.2023
 */
class SharedPrefsAuthentication(context: Context) {

    private companion object {
        private const val PREFS_NAME = "featureTogglesCache"
        private const val PREFS_AUTHENTICATION_KEY = "featureTogglesKey"
    }

    private val featureTogglesCachedSharedPreferences by lazy { context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE) }

    fun saveAuthentication(isAuthentication: Boolean) {
        featureTogglesCachedSharedPreferences.edit().apply {
            putBoolean(PREFS_AUTHENTICATION_KEY, isAuthentication)
            apply()
        }
    }

    fun getAuthentication(): Boolean {
        return featureTogglesCachedSharedPreferences.getBoolean(PREFS_AUTHENTICATION_KEY, false)
    }

    fun clearAuthentication() {
        featureTogglesCachedSharedPreferences.edit().clear().apply()
    }
}