package com.example.sass.data.datasource

import java.text.SimpleDateFormat
import java.util.*

object StringUtils {

    fun dateFormatter(milliseconds: Long): String {
        return SimpleDateFormat(FORMAT_PATTERN).format(Date(milliseconds)).toString()
    }

    fun formatToken(token: String): String {
        return "Token $token"
    }

    private const val FORMAT_PATTERN = "dd.MM.yyyy"
}