package com.example.myfaith.api

import com.example.myfaith.model.api.LoginApi
import com.example.myfaith.model.entity.response.LoginResponse
import okhttp3.OkHttpClient
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue

class LoginApiTest {

    private lateinit var loginApi: LoginApi

    @Before
    fun setup() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://example.com/")
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        loginApi = retrofit.create(LoginApi::class.java)
    }

    @Test
    fun `test_loginApi_is_created`() {
        assertNotNull(loginApi)
    }

    @Test
    fun `test_loginUser_returns_Call_object`() {
        val call: Call<LoginResponse> = loginApi.loginUser("test@example.com", "password123")
        assertNotNull(call)
        assertTrue(call is Call<*>) // Проверка, что это объект типа Call
    }
}
