package com.example.sass.data.user

import com.example.sass.data.user.datasource.local.models.UserInfoDao
import com.example.sass.data.user.datasource.remote.models.UserInfoDto
import com.example.sass.domain.models.UserInfo
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

    fun mapUserInfoDaoToUserInfo(userInfoDao: UserInfoDao): UserInfo {
        return UserInfo(
            about = userInfoDao.about,
            avatar = userInfoDao.avatar,
            city = userInfoDao.city,
            email = userInfoDao.email,
            firstName = userInfoDao.firstName,
            id = userInfoDao.id,
            lastName = userInfoDao.lastName,
            phone = userInfoDao.phone
        )
    }
}