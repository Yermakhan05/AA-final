package com.example.myfaith.model.api

import com.example.myfaith.model.entity.response.QuranSurahResponse
import retrofit2.Response
import retrofit2.http.GET

interface QuranApi {
    @GET("/qurans/surah")
    suspend fun getQuranSurah(): Response<QuranSurahResponse>
}
