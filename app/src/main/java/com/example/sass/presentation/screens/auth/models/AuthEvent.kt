package com.example.sass.presentation.screens.auth.models

sealed class AuthEvent {
    data class OnSignInEvent(val login: String, val password: String) : AuthEvent()

    data class OnInvalidateLoginEvent(val message: String) : AuthEvent()
    data class OnInvalidatePasswordEvent(val message: String) : AuthEvent()

    data class OnValidateSignInData(val login: String, val password: String): AuthEvent()

    object OnInitLoginErrorEvent : AuthEvent()
    object OnInitPasswordErrorEvent : AuthEvent()

}
