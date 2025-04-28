package com.example.myfaith.api

import com.example.myfaith.entity.response.QuoteResponse
import retrofit2.Response
import retrofit2.http.GET

interface QuoteApi {
    @GET("/quote")
    suspend fun getTodayQuote(): Response<QuoteResponse>
}
