package com.example.sass.data.mapper

import com.example.sass.data.datasource.local.user.models.UserInfoDao
import com.example.sass.data.datasource.remote.user.models.UserInfoDto
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
            about = formatUserAbout(userInfoDao.about),
            avatar = userInfoDao.avatar,
            city = userInfoDao.city,
            email = userInfoDao.email,
            firstName = userInfoDao.firstName,
            id = userInfoDao.id,
            lastName = userInfoDao.lastName,
            phone = formatUserPhone(userInfoDao.phone)
        )
    }

    private fun formatUserPhone(phone: String): String {
        val string = phone.replace("[^\\d+]".toRegex(), EMPTY_REPLACEMENT)

        if (string.length != PHONE_LENGTH) return phone

        val phoneCodeRange = (2..4)
        val secondPartRange = (5..7)
        val thirdPartRange = (8..9)
        val lastPartRange = (10 until PHONE_LENGTH)

        return buildString {
            append("+ $COUNTRY_CODE (")
            append(string.substring(phoneCodeRange))
            append(") ")
            append(string.substring(secondPartRange))
            append(" ")
            append(string.substring(thirdPartRange))
            append(" ")
            append(string.substring(lastPartRange))
        }
    }

    private fun formatUserAbout(about: String): String {
        return buildString {
            append("\"")
            append(about)
            append("\"")
        }
    }

    companion object {
        private const val EMPTY_REPLACEMENT = ""

        private const val COUNTRY_CODE = "7"
        private const val PHONE_LENGTH = 12
    }
}