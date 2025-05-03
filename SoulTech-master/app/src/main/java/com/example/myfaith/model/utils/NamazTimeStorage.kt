package com.example.myfaith.model.utils

import android.content.Context

class NamazTimeStorage(context: Context) {
    private val prefs = context.getSharedPreferences("namaz_times", Context.MODE_PRIVATE)

    fun saveTimes(
        fajr: String,
        dhuhr: String,
        asr: String,
        maghrib: String,
        isha: String,
        date: String
    ) {
        prefs.edit().apply {
            putString("fajr", fajr)
            putString("dhuhr", dhuhr)
            putString("asr", asr)
            putString("maghrib", maghrib)
            putString("isha", isha)
            putString("date", date)
            apply()
        }
    }

    fun getSavedDate(): String? = prefs.getString("date", null)

    fun getTimes(): Map<String, String?> = mapOf(
        "fajr" to prefs.getString("fajr", null),
        "dhuhr" to prefs.getString("dhuhr", null),
        "asr" to prefs.getString("asr", null),
        "maghrib" to prefs.getString("maghrib", null),
        "isha" to prefs.getString("isha", null)
    )

    fun saveNextPrayer(name: String, time: String) {
        prefs.edit().apply {
            putString("next_prayer_name", name)
            putString("next_prayer_time", time)
            apply()
        }
    }

    fun getNextPrayer(): Pair<String?, String?> {
        val name = prefs.getString("next_prayer_name", null)
        val time = prefs.getString("next_prayer_time", null)
        return Pair(name, time)
    }

}
