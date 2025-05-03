package com.example.myfaith.model.datasource

import retrofit2.Call
import retrofit2.http.GET
import com.example.myfaith.model.entity.Quote

interface HadithApiService {
    @GET("api/hadiths/") // Путь к твоему API
    fun getHadiths(): Call<List<Quote>>
}
