package com.example.myfaith.receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.myfaith.AzanService
import com.example.myfaith.R
import com.example.myfaith.model.utils.NotificationHelper

class NamazAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val prayerName = intent?.getStringExtra("PRAYER_NAME") ?: "Namaz Time"
        val azanIntent = Intent(context, AzanService::class.java)
        azanIntent.putExtra("PRAYER_NAME", prayerName)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ContextCompat.startForegroundService(context, azanIntent)
        } else {
            context.startService(azanIntent)
        }
    }
}
