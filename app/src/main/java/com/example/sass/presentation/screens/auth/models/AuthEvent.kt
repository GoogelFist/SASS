package com.example.sass.presentation.screens.auth.models

sealed class AuthEvent {
    data class OnSignIn(val login: String, val password: String) : AuthEvent()
}
