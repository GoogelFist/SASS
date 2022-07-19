package com.example.sass.presentation.screens.auth

object AuthStringUtils {

    fun formatPhone(login: String): String {
        val text = login.replace("[^\\d]".toRegex(), REPLACEMENT)
        return "$PHONE_COUNTRY_CODE$text"
    }

    private const val PHONE_COUNTRY_CODE = "+7"
    private const val REPLACEMENT = ""
}