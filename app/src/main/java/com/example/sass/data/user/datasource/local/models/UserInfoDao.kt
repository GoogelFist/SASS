package com.example.sass.data.user.datasource.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user_info")
data class UserInfoDao(

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
)

private const val USER_DB_ID = 1