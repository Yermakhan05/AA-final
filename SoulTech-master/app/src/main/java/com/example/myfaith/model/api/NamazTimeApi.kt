package com.example.myfaith.model.api

import com.example.myfaith.model.entity.response.NamazTimeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NamazTimeApi {
    @GET("prayer/get-prayer-times")
    suspend fun getNamazTimes(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("date") date: String // format: YYYY-MM-DD

    ): Response<NamazTimeResponse>
}
