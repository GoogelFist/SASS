package com.example.sass.presentation.screens.auth.models

sealed class AuthState {
    object SingInErrorState : AuthState()

    object InitLoginErrorState : AuthState()
    object InitPasswordErrorState : AuthState()

    data class InvalidateLoginErrorState(val message: String) : AuthState()
    data class InvalidatePasswordErrorState(val message: String) : AuthState()

    data class ValidatedState(val login: String, val password: String) : AuthState()

    object SigningState: AuthState()
    object SignedState: AuthState()
}