package com.example.sass.presentation.screens.auth.models

sealed class AuthState {

    object SigningIn : AuthState()
    object SignedIn : AuthState()

    data class SingInValidateError(
        val loginErrorSubState: ErrorLoginSubState = ErrorLoginSubState.Default,
        val passwordErrorSubState: ErrorPasswordSubState = ErrorPasswordSubState.Default
    ) : AuthState()

    object SingInError : AuthState()
    object Error : AuthState()
}

sealed class ErrorLoginSubState {
    object Default : ErrorLoginSubState()
    object IsBlank : ErrorLoginSubState()
    object Invalidate : ErrorLoginSubState()
}

sealed class ErrorPasswordSubState {
    object Default : ErrorPasswordSubState()
    object IsBlank : ErrorPasswordSubState()
    object Invalidate : ErrorPasswordSubState()
}