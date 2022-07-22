package com.example.sass.data.datasource.remote.user.models

import com.example.sass.data.datasource.local.user.models.UserInfoDbEntity

data class SignInDto(val token: String, val userInfoDbEntity: UserInfoDbEntity)
