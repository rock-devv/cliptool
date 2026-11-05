package com.example.pastebinshare

import android.content.Context

/** Persistent app preferences. */
class Settings(context: Context) {

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    /** Whether the uploaded link should be opened in a browser. */
    var openLink: Boolean
        get() = prefs.getBoolean(KEY_OPEN_LINK, false)
        set(value) {
            prefs.edit().putBoolean(KEY_OPEN_LINK, value).apply()
        }

    /** How long the paste should be kept, in days. */
    var expiryDays: Int
        get() = prefs.getInt(KEY_EXPIRY_DAYS, DEFAULT_EXPIRY_DAYS)
        set(value) {
            prefs.edit().putInt(KEY_EXPIRY_DAYS, value).apply()
        }

    companion object {
        const val DEFAULT_EXPIRY_DAYS = 7

        private const val PREFS_NAME = "settings"
        private const val KEY_OPEN_LINK = "open_link"
        private const val KEY_EXPIRY_DAYS = "expiry_days"
    }
}
