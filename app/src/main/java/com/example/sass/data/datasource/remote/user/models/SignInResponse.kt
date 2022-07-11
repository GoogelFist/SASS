package com.example.sass.data.datasource.remote.user.models

import com.google.gson.annotations.SerializedName

data class SignInResponse(
    @SerializedName("token")
    val token: String,
    @SerializedName("user_info")
    val userInfoDTO: UserInfoDto
)