package com.bdkjupiter.sensi.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.bdkjupiter.sensi.data.GenerationRecord

class PrefsManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("bdk_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun isLoggedIn(): Boolean = prefs.getBoolean("logged_in", false)

    fun setLoggedIn(state: Boolean, password: String = "") {
        prefs.edit()
            .putBoolean("logged_in", state)
            .putString("active_password", password)
            .apply()
    }

    fun lockPasswordToDevice(password: String, deviceId: String) {
        prefs.edit().putString("device_$password", deviceId).apply()
    }

    fun getLockedDevice(password: String): String? {
        return prefs.getString("device_$password", null)
    }

    fun isDarkMode(): Boolean = prefs.getBoolean("dark_mode", true)

    fun setDarkMode(enabled: Boolean) {
        prefs.edit().putBoolean("dark_mode", enabled).apply()
    }

    fun saveHistory(record: GenerationRecord) {
        val list = getHistory().toMutableList()
        list.add(0, record) // newest first
        if (list.size > 50) list.removeAt(list.size - 1) // cap at 50
        prefs.edit().putString("history", gson.toJson(list)).apply()
    }

    fun getHistory(): List<GenerationRecord> {
        val json = prefs.getString("history", null) ?: return emptyList()
        val type = object : TypeToken<List<GenerationRecord>>() {}.type
        return gson.fromJson(json, type) ?: emptyList()
    }

    fun clearHistory() {
        prefs.edit().remove("history").apply()
    }

    fun getLastRecord(): GenerationRecord? {
        return getHistory().firstOrNull()
    }
}
