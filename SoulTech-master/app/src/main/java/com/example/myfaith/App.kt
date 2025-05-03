package com.example.myfaith

import android.app.Application
import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.myfaith.model.datasource.ApiSource
import com.example.myfaith.model.utils.LocaleHelper
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        ApiSource.init(applicationContext)
        val workManager = WorkManager.getInstance(applicationContext)

        val dailyRequest = PeriodicWorkRequestBuilder<NamazUpdateWorker>(1, TimeUnit.DAYS)
            .setInitialDelay(1, TimeUnit.HOURS)
            .addTag("namaz_daily_update")
            .build()

        workManager.enqueueUniquePeriodicWork(
            "NamazTimeUpdate",
            ExistingPeriodicWorkPolicy.KEEP,
            dailyRequest
        )
    }
    override fun attachBaseContext(base: Context?) {
        val lang = base?.getSharedPreferences("settings", Context.MODE_PRIVATE)
            ?.getString("lang", "kk") ?: "kk"
        val context = base?.let { LocaleHelper.setLocale(it, lang) }
        super.attachBaseContext(context)
    }
}
