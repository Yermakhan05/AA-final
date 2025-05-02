package com.example.myfaith

import android.app.*
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.myfaith.R
import com.example.myfaith.receiver.StopAzanReceiver

class AzanService : Service() {

    private lateinit var mediaPlayer: MediaPlayer

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val prayerName = intent?.getStringExtra("PRAYER_NAME") ?: "Namaz Time"

        startForeground(1, createNotification(prayerName))

        mediaPlayer = MediaPlayer.create(this, R.raw.azan)
        mediaPlayer.start()

        mediaPlayer.setOnCompletionListener {
            stopSelf()
        }

        return START_NOT_STICKY
    }

    private fun createNotification(prayerName: String): Notification {
        val stopIntent = Intent(this, StopAzanReceiver::class.java)
        val stopPendingIntent = PendingIntent.getBroadcast(this, 0, stopIntent, PendingIntent.FLAG_IMMUTABLE)

        return NotificationCompat.Builder(this, "azan_channel")
            .setContentTitle("Время $prayerName")
            .setContentText("Прозвучит азан")
            .setSmallIcon(R.drawable.compass)
            .addAction(R.drawable.baseline_stop_circle_24, "Остановить", stopPendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::mediaPlayer.isInitialized && mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            mediaPlayer.release()
        }
    }

    override fun onBind(intent: Intent?): IBinder? = null
}