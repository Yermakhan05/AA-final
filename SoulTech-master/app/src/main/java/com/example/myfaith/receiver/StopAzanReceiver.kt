package com.example.myfaith.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.myfaith.AzanService

class StopAzanReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        context.stopService(Intent(context, AzanService::class.java))
    }
}