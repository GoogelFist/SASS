package com.example.sass.data

import com.example.sass.data.datasourse.local.models.UserInfoDao
import com.example.sass.data.datasourse.remote.models.UserInfoDto
import javax.inject.Inject

class Mapper @Inject constructor() {

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