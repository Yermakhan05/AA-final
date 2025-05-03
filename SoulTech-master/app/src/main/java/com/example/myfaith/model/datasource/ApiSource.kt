package com.example.myfaith.model.datasource

import RegistrationApi
import android.content.Context
import com.example.myfaith.model.api.LoginApi
import com.example.myfaith.model.api.NamazTimeApi
import com.example.myfaith.model.api.ProfileApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiSource {
    private const val BASE_URL = "http://10.0.2.2:8000/"

    private lateinit var authClient: OkHttpClient
    private lateinit var authRetrofit: Retrofit
    private lateinit var plainRetrofit: Retrofit

    fun init(context: Context) {

        authClient = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(context))
            .build()

        authRetrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(authClient)
            .build()

        plainRetrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    val login: LoginApi by lazy {
        plainRetrofit.create(LoginApi::class.java)
    }

    val registration: RegistrationApi by lazy {
        plainRetrofit.create(RegistrationApi::class.java)
    }

    val namazTimeApi: NamazTimeApi by lazy {
        authRetrofit.create(NamazTimeApi::class.java)
    }

    val profileApi: ProfileApi by lazy {
        authRetrofit.create(ProfileApi:: class.java)
    }

}
