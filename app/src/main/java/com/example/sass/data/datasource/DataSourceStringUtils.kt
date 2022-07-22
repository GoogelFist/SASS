package com.example.sass.data.datasource

import java.text.SimpleDateFormat
import java.util.*

object DataSourceStringUtils {

    fun dateFormatter(milliseconds: Long): String {
        return SimpleDateFormat(FORMAT_PATTERN).format(Date(milliseconds)).toString()
    }

    fun formatToken(token: String): String {
        return "Token $token"
    }

    fun formatUserPhone(phone: String): String {
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

    fun formatUserAbout(about: String): String {
        return buildString {
            append("\"")
            append(formatUserData(about))
            append("\"")
        }
    }

    fun formatUserData(string: String): String {
        return string.trim().capitalizeString()
    }

    private fun String.capitalizeString(): String {
        return this.replaceFirstChar { char ->
            if (char.isLowerCase()) {
                char.titlecase(Locale.getDefault())
            } else {
                char.toString()
            }
        }
    }

    private const val EMPTY_REPLACEMENT = ""

    private const val COUNTRY_CODE = "7"
    private const val PHONE_LENGTH = 12

    private const val FORMAT_PATTERN = "dd.MM.yyyy"
}