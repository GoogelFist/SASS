package com.example.sass.presentation.screens.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sass.domain.SingInUseCase
import com.example.sass.presentation.screens.EventHandler
import com.example.sass.presentation.screens.auth.models.AuthEvent
import com.example.sass.presentation.screens.auth.models.AuthState
import kotlinx.coroutines.launch

class AuthViewModel(private val singInUseCase: SingInUseCase) : ViewModel(),
    EventHandler<AuthEvent> {

    private var _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState>
        get() = _authState

    override fun obtainEvent(event: AuthEvent) {
        when (event) {
            AuthEvent.OnDefaultState -> setDefaultState()

            is AuthEvent.OnSignIn -> signedIn(event.login, event.password)

            is AuthEvent.OnInvalidateLogin -> invalidatedLogin(event.message)
            is AuthEvent.OnInvalidatePassword -> invalidatedPassword(event.message)

            is AuthEvent.OnValidateSignInData -> validatedSignInData(event.login, event.password)

            AuthEvent.OnInitLoginError -> initLoginError()
            AuthEvent.OnInitPasswordError -> initPasswordError()
        }
    }

    private fun signedIn(login: String, password: String) {

        viewModelScope.launch {
            try {
                setDefaultState()
                _authState.value = AuthState.SigningIn

                singInUseCase(login, password)
                _authState.value = AuthState.SignedIn
            } catch (error: Throwable) {
                _authState.value = AuthState.SingInError
            }
        }
    }

    private fun setDefaultState() {
        _authState.value = AuthState.Default
    }

    private fun invalidatedLogin(message: String) {
        _authState.value = AuthState.InvalidateLoginError(message)
    }

    private fun invalidatedPassword(message: String) {
        _authState.value = AuthState.InvalidatePasswordError(message)
    }

    private fun validatedSignInData(login: String, password: String) {
        _authState.value = AuthState.Validated(login, password)
    }

    private fun initLoginError() {
        _authState.value = AuthState.InitLoginError
    }

    private fun initPasswordError() {
        _authState.value = AuthState.InitPasswordError
    }
}
