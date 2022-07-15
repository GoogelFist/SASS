package com.example.sass.presentation.screens.auth

import com.example.sass.presentation.screens.auth.models.ErrorLoginSubState
import com.example.sass.presentation.screens.auth.models.ErrorPasswordSubState

object AuthHelper {

    fun validateLogin(login: String): ErrorLoginSubState {
        val regex = Regex(PATTERN)

        return when {
            login.isBlank() -> ErrorLoginSubState.IsBlank

            !login.matches(regex) || login.length != LOGIN_LENGTH_CONSTRAINT -> {
                ErrorLoginSubState.Invalidate
            }
            else -> ErrorLoginSubState.Default
        }
    }

    fun validatePassword(password: String): ErrorPasswordSubState {
        return when {
            password.isBlank() -> ErrorPasswordSubState.IsBlank

            password.length !in PASSWORD_MIN_LENGTH_CONSTRAINT..PASSWORD_MAN_LENGTH_CONSTRAINT -> {
                ErrorPasswordSubState.Invalidate
            }
            else -> ErrorPasswordSubState.Default
        }
    }

    fun formatPhone(login: String): String {
        val text = login.replace("[^\\d]".toRegex(), REPLACEMENT)
        return "$PHONE_COUNTRY_CODE$text"
    }

    private const val PATTERN = "[+]?[78]?[() 0-9-]+"

    private const val LOGIN_LENGTH_CONSTRAINT = 16

    private const val PASSWORD_MIN_LENGTH_CONSTRAINT = 6
    private const val PASSWORD_MAN_LENGTH_CONSTRAINT = 255

    private const val PHONE_COUNTRY_CODE = "+7"

    private const val REPLACEMENT = ""
}