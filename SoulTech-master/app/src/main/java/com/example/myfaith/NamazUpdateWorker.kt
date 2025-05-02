package com.example.myfaith

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.myfaith.datasource.ApiSource
import com.example.myfaith.utils.LocationHelper
import com.example.myfaith.utils.NamazTimeStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import java.time.LocalDate

class NamazUpdateWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    private val namazStorage = NamazTimeStorage(context)
    private val locationHelper = LocationHelper(context)

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun doWork(): Result {
        val date = LocalDate.now().toString()

        return suspendCancellableCoroutine { cont ->
            locationHelper.getCurrentLocation { location ->
                if (location == null) {
                    cont.resume(Result.retry(), null)
                    return@getCurrentLocation
                }

                val latitude = location.latitude
                val longitude = location.longitude

                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val response = ApiSource.namazTimeApi.getNamazTimes(latitude, longitude, date)
                        if (response.isSuccessful) {
                            response.body()?.let {
                                namazStorage.saveTimes(
                                    it.Fajr, it.Dhuhr, it.Asr, it.Maghrib, it.Isha, date
                                )
                                cont.resume(Result.success(), null)
                            } ?: cont.resume(Result.failure(), null)
                        } else {
                            cont.resume(Result.retry(), null)
                        }
                    } catch (e: Exception) {
                        cont.resume(Result.retry(), null)
                    }
                }
            }
        }
    }
}