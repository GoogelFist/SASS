package com.example.sass.presentation.screens.auth.models

sealed class AuthEvent {
    object OnClearUserData: AuthEvent()

    data class OnSignIn(val login: String, val password: String) : AuthEvent()
}
