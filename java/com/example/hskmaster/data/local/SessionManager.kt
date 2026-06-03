package com.example.hskmaster.data.local

import android.content.Context

object SessionManager {
    private const val PREFS = "hsk_prefs"
    private const val KEY_USERNAME = "key_username"

    fun saveUsername(context: Context, username: String) {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_USERNAME, username).apply()
    }

    fun getUsername(context: Context): String? {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        return prefs.getString(KEY_USERNAME, null)
    }

    fun clear(context: Context) {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        prefs.edit().remove(KEY_USERNAME).apply()
    }
}

