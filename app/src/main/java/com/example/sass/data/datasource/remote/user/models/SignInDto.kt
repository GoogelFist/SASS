package com.example.sass.data.datasource.remote.user.models

import com.example.sass.data.datasource.local.user.models.UserInfoDao

data class SignInDto(val token: String, val userInfoDao: UserInfoDao)
