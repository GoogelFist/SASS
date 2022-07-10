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
                _authState.value = AuthState.SigningInState

                singInUseCase(login, password)
                _authState.value = AuthState.SignedInState
            } catch (error: Throwable) {
                _authState.value = AuthState.SingInErrorState
            }
        }
    }

    private fun setDefaultState() {
        _authState.value = AuthState.DefaultState
    }

    private fun invalidatedLogin(message: String) {
        _authState.value = AuthState.InvalidateLoginErrorState(message)
    }

    private fun invalidatedPassword(message: String) {
        _authState.value = AuthState.InvalidatePasswordErrorState(message)
    }

    private fun validatedSignInData(login: String, password: String) {
        _authState.value = AuthState.ValidatedState(login, password)
    }

    private fun initLoginError() {
        _authState.value = AuthState.InitLoginErrorState
    }

    private fun initPasswordError() {
        _authState.value = AuthState.InitPasswordErrorState
    }
}
