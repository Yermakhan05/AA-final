package com.example.myfaith.model.entity.response

import com.google.gson.annotations.SerializedName

data class ProfileResponse(
    @SerializedName("full_name")
    val fullName: String,
    val number: String?,
    val bio: String?,
    @SerializedName("profile_picture")
    val profilePicture: String?
)
