package com.example.myfaith.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.myfaith.utils.NotificationHelper

class NamazAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val prayerName = intent.getStringExtra("PRAYER_NAME") ?: "Prayer"
        NotificationHelper(context).showNotification("Time for $prayerName", "It's time for $prayerName prayer.")
    }
}
