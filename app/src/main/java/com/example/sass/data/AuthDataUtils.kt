package com.example.sass.data

object AuthDataUtils {

    fun validateSignInData(login: String, password: String) {
        val regex = Regex(PATTERN)

        when {
            login.isBlank() -> throw BlankLoginException()

            password.isBlank() -> throw BlankPasswordException()

            !login.matches(regex) || login.length != LOGIN_LENGTH_CONSTRAINT -> {
                throw InvalidateLoginException()
            }
            password.length !in PASSWORD_MIN_LENGTH_CONSTRAINT..PASSWORD_MAN_LENGTH_CONSTRAINT -> {
                throw InvalidatePasswordException()
            }
        }
    }

    fun formatPhone(login: String): String {
        val text = login.replace("[^\\d]".toRegex(), "")
        return "${PHONE_COUNTRY_CODE}$text"
    }

    private const val PATTERN = "[+]?[78]?[() 0-9-]+"

    private const val LOGIN_LENGTH_CONSTRAINT = 16

    private const val PASSWORD_MIN_LENGTH_CONSTRAINT = 6
    private const val PASSWORD_MAN_LENGTH_CONSTRAINT = 255

    private const val PHONE_COUNTRY_CODE = "+7"
}