package com.example.sass.data.user

import com.example.sass.data.user.datasource.local.models.UserInfoDao
import com.example.sass.data.user.datasource.remote.models.UserInfoDto
import javax.inject.Inject

class UserMapper @Inject constructor() {

    fun mapUserInfoDtoToUserInfoDao(userInfoDto: UserInfoDto): UserInfoDao {
        return UserInfoDao(
            about = userInfoDto.about,
            avatar = userInfoDto.avatar,
            city = userInfoDto.city,
            email = userInfoDto.email,
            firstName = userInfoDto.firstName,
            id = userInfoDto.id,
            lastName = userInfoDto.lastName,
            phone = userInfoDto.phone
        )
    }
}