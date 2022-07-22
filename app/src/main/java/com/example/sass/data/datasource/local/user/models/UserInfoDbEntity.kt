package com.example.sass.data.datasource.local.user.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.sass.data.datasource.DataSourceStringUtils
import com.example.sass.data.datasource.remote.user.models.UserInfoDto
import com.example.sass.domain.models.UserInfo


@Entity(tableName = "user_info")
data class UserInfoDbEntity(

    @ColumnInfo(name = "prime_id")
    @PrimaryKey
    val user_db_id: Int = USER_DB_ID,

    @ColumnInfo(name = "about")
    val about: String = "",

    @ColumnInfo(name = "avatar")
    val avatar: String = "",

    @ColumnInfo(name = "city")
    val city: String = "",

    @ColumnInfo(name = "email")
    val email: String = "",

    @ColumnInfo(name = "first_name")
    val firstName: String = "",

    @ColumnInfo(name = "id")
    val id: String = "",

    @ColumnInfo(name = "last_name")
    val lastName: String = "",

    @ColumnInfo(name = "phone")
    val phone: String = ""
) {

    fun toUserInfo(): UserInfo {
        return UserInfo(
            about = DataSourceStringUtils.formatUserAbout(about),
            avatar = avatar,
            city = DataSourceStringUtils.formatUserData(city),
            email = email,
            firstName = DataSourceStringUtils.formatUserData(firstName),
            id = id,
            lastName = DataSourceStringUtils.formatUserData(lastName),
            phone = DataSourceStringUtils.formatUserPhone(phone)
        )
    }

    companion object {
        private const val USER_DB_ID = 1

        fun fromUserInfoDto(userInfoDto: UserInfoDto): UserInfoDbEntity {
            return UserInfoDbEntity(
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
}
