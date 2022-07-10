package com.example.sass.presentation.screens.auth.models

sealed class AuthEvent {
    data class OnSignIn(val login: String, val password: String) : AuthEvent()
    object OnDefaultState: AuthEvent()

    data class OnInvalidateLogin(val message: String) : AuthEvent()
    data class OnInvalidatePassword(val message: String) : AuthEvent()

    data class OnValidateSignInData(val login: String, val password: String): AuthEvent()

    object OnInitLoginError : AuthEvent()
    object OnInitPasswordError : AuthEvent()
}
