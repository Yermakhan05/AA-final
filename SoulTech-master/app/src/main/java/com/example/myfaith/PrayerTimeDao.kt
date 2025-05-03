package com.example.myfaith

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PrayerTimeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPrayerTimes(prayerTimes: List<PrayerTimeEntity>)

    @Query("SELECT * FROM prayer_times WHERE date = :date AND latitude BETWEEN :lat - 0.1 AND :lat + 0.1 AND longitude BETWEEN :lng - 0.1 AND :lng + 0.1")
    fun getPrayerTimesByDateAndLocation(date: String, lat: Double, lng: Double): Flow<List<PrayerTimeEntity>>

    @Query("DELETE FROM prayer_times WHERE lastUpdated < :timestamp")
    suspend fun deleteOldPrayerTimes(timestamp: Long)

    @Query("SELECT * FROM prayer_times WHERE date >= :startDate AND date <= :endDate")
    fun getPrayerTimesByDateRange(startDate: String, endDate: String): Flow<List<PrayerTimeEntity>>
}