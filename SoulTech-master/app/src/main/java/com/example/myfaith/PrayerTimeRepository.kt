package com.example.myfaith

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PrayerTimeRepository @Inject constructor(
    private val prayerTimeDao: PrayerTimeDao
) {
    fun getPrayerTimes(date: String, latitude: Double, longitude: Double): Flow<List<PrayerTimeEntity>> {
        return prayerTimeDao.getPrayerTimesByDateAndLocation(date, latitude, longitude)
    }

    suspend fun savePrayerTimes(prayerTimes: List<PrayerTimeEntity>) {
        prayerTimeDao.insertPrayerTimes(prayerTimes)
    }

    fun getPrayerTimesByDateRange(startDate: String, endDate: String): Flow<List<PrayerTimeEntity>> {
        return prayerTimeDao.getPrayerTimesByDateRange(startDate, endDate)
    }

    suspend fun cleanOldData() {
        val thirtyDaysAgo = System.currentTimeMillis() - (30 * 24 * 60 * 60 * 1000)
        prayerTimeDao.deleteOldPrayerTimes(thirtyDaysAgo)
    }
}