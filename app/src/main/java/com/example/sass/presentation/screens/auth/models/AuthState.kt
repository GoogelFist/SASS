package com.example.sass.presentation.screens.auth.models

sealed class AuthState {
    object Default: AuthState()

    object SigningIn: AuthState()
    object SignedIn: AuthState()

    data class SingInLoginError(val subState: ErrorLoginSubState) : AuthState()
    data class SingInPasswordError(val subState: ErrorPasswordSubState) : AuthState()

    object SingInError : AuthState()
}

sealed class ErrorLoginSubState {
    object Default: ErrorLoginSubState()
    object IsBlank: ErrorLoginSubState()
    object Invalidate: ErrorLoginSubState()
}

sealed class ErrorPasswordSubState {
    object Default: ErrorPasswordSubState()
    object IsBlank: ErrorPasswordSubState()
    object Invalidate: ErrorPasswordSubState()
}