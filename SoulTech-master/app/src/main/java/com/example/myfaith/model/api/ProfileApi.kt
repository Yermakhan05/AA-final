package com.example.myfaith.model.api

import com.example.myfaith.model.entity.response.ProfileResponse
import okhttp3.RequestBody
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PUT
import retrofit2.http.Part

interface ProfileApi {

    @GET("users/profile/")
    suspend fun getProfile(): Response<ProfileResponse>

    @Multipart
    @PUT("users/profile/")
    fun updateProfile(
        @Part("full_name") fullName: RequestBody,
        @Part("number") number: RequestBody,
        @Part("bio") bio: RequestBody,
        @Part("profile_picture") profilePictureBase64: RequestBody?
    ): Call<ProfileResponse>
}
