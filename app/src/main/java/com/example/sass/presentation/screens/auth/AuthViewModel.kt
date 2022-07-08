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
            is AuthEvent.OnSignInEvent -> signedIn(event.login, event.password)

            is AuthEvent.OnInvalidateLoginEvent -> invalidatedLogin(event.message)
            is AuthEvent.OnInvalidatePasswordEvent -> invalidatedPassword(event.message)

            is AuthEvent.OnValidateSignInData -> validatedSignInData(event.login, event.password)

            AuthEvent.OnInitLoginErrorEvent -> initLoginError()
            AuthEvent.OnInitPasswordErrorEvent -> initPasswordError()
        }
    }

    private fun signedIn(login: String, password: String) {

        viewModelScope.launch {
            try {
                _authState.postValue(AuthState.SigningState)
                singInUseCase(login, password)
                _authState.postValue(AuthState.SignedState)

            } catch (err: RuntimeException) {
                _authState.postValue(AuthState.SingInErrorState)
            }
        }
    }

    private fun invalidatedLogin(message: String) {
        _authState.postValue(AuthState.InvalidateLoginErrorState(message))
    }

    private fun invalidatedPassword(message: String) {
        _authState.postValue(AuthState.InvalidatePasswordErrorState(message))
    }

    private fun validatedSignInData(login: String, password: String) {
        _authState.postValue(AuthState.ValidatedState(login, password))
    }

    private fun initLoginError() {
        _authState.postValue(AuthState.InitLoginErrorState)
    }

    private fun initPasswordError() {
        _authState.postValue(AuthState.InitPasswordErrorState)
    }
}
