package com.example.sass.presentation.screens.auth.models

sealed class AuthState {
    object Default: AuthState()

    object SingInError : AuthState()

    object InitLoginError : AuthState()
    object InitPasswordError : AuthState()

    data class InvalidateLoginError(val message: String) : AuthState()
    data class InvalidatePasswordError(val message: String) : AuthState()

    data class Validated(val login: String, val password: String) : AuthState()

    object SigningIn: AuthState()
    object SignedIn: AuthState()
}