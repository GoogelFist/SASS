package com.example.sass.data.user.datasource.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user_info")
data class UserInfoDao(

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

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String = "",

    @ColumnInfo(name = "last_name")
    val lastName: String = "",

    @ColumnInfo(name = "phone")
    val phone: String = ""
)