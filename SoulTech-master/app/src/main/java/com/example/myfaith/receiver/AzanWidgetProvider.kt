package com.example.myfaith.receiver

import android.annotation.SuppressLint
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.preference.PreferenceManager
import android.util.Log
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.myfaith.R
import com.example.myfaith.R.layout.azan_widget
import com.example.myfaith.model.utils.NamazTimeStorage
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

class AzanWidgetProvider : AppWidgetProvider() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (widgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, widgetId)
        }
    }

    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            val namazStorage = NamazTimeStorage(context)
            val views = RemoteViews(context.packageName, R.layout.azan_widget)

            val savedDate = namazStorage.getSavedDate()
            val currentDate = LocalDate.now().toString()

            if (savedDate == currentDate) {
                views.setTextViewText(R.id.widget_title, currentDate)
                views.setTextColor(R.id.widget_title, Color.GREEN)
                // Load from local
                val times = namazStorage.getTimes()
                // Пример установки времени намазов — можно получить из базы или API
                views.setTextViewText(R.id.widget_fajr, times["fajr"])
                views.setTextViewText(R.id.widget_dhuhr, times["dhuhr"])
                views.setTextViewText(R.id.widget_asr, times["asr"])
                views.setTextViewText(R.id.widget_maghrib, times["maghrib"])
                views.setTextViewText(R.id.widget_isha, times["isha"])

                val (nextPrayerName, nextPrayerTime) = namazStorage.getNextPrayer()

                Log.d("NextPrayer", "Next: $nextPrayerName at $nextPrayerTime")
                val currentTime = LocalTime.now()

                val fajrTime = LocalTime.parse(times["fajr"])
                val dhuhrTime = LocalTime.parse(times["dhuhr"])
                val asrTime = LocalTime.parse(times["asr"])
                val maghribTime = LocalTime.parse(times["maghrib"])
                val ishaTime = LocalTime.parse(times["isha"])

                val timeMap = mapOf(
                    "Fajr" to fajrTime,
                    "Dhuhr" to dhuhrTime,
                    "Asr" to asrTime,
                    "Maghrib" to maghribTime,
                    "Isha" to ishaTime
                )

                // Find the next prayer time after the current time
                val nextPrayer =
                    timeMap.filterValues { it.isAfter(currentTime) }.minByOrNull { it.value }

                // If the next prayer has changed, update the color and save it again
                nextPrayer?.let { entry ->
                    when (entry.key) {
                        "Fajr" -> views.setTextColor(R.id.widget_fajr, Color.GREEN)
                        "Dhuhr" -> views.setTextColor(R.id.widget_dhuhr, Color.GREEN)
                        "Asr" -> views.setTextColor(R.id.widget_asr, Color.GREEN)
                        "Maghrib" -> views.setTextColor(R.id.widget_maghrib, Color.GREEN)
                        "Isha" -> views.setTextColor(R.id.widget_isha, Color.GREEN)
                    }

                    namazStorage.saveNextPrayer(entry.key, entry.value.toString())

                }
                // Добавить остальные намазы аналогично

                // Обновление виджета
                appWidgetManager.updateAppWidget(appWidgetId, views)
            }
        }
    }
}