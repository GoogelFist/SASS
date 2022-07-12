package com.example.sass.presentation.screens.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sass.data.BlankLoginException
import com.example.sass.data.BlankPasswordException
import com.example.sass.data.InvalidateLoginException
import com.example.sass.data.InvalidatePasswordException
import com.example.sass.domain.SingInUseCase
import com.example.sass.presentation.screens.EventHandler
import com.example.sass.presentation.screens.auth.models.AuthEvent
import com.example.sass.presentation.screens.auth.models.AuthState
import com.example.sass.presentation.screens.auth.models.ErrorLoginSubState
import com.example.sass.presentation.screens.auth.models.ErrorPasswordSubState
import kotlinx.coroutines.launch

class AuthViewModel(private val singInUseCase: SingInUseCase) : ViewModel(),
    EventHandler<AuthEvent> {

    private var _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState>
        get() = _authState

    override fun obtainEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.OnSignIn -> signedIn(event.login, event.password)
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
                setDefaultState()
                when (error) {
                    is BlankLoginException -> blankLogin()
                    is BlankPasswordException -> blankPassword()
                    is InvalidateLoginException -> invalidatedLogin()
                    is InvalidatePasswordException -> invalidatedPassword()
                    else -> _authState.value = AuthState.SingInError
                }
            }
        }
    }

    private fun setDefaultState() {
        _authState.value = AuthState.Default
    }

    private fun blankLogin() {
        setDefaultPasswordErrorState()
        _authState.value = AuthState.SingInLoginError(ErrorLoginSubState.IsBlank)
    }

    private fun blankPassword() {
        setDefaultLoginErrorState()
        _authState.value = AuthState.SingInPasswordError(ErrorPasswordSubState.IsBlank)
    }

    private fun invalidatedLogin() {
        setDefaultPasswordErrorState()
        _authState.value = AuthState.SingInLoginError(ErrorLoginSubState.Invalidate)
    }

    private fun invalidatedPassword() {
        setDefaultLoginErrorState()
        _authState.value = AuthState.SingInPasswordError(ErrorPasswordSubState.Invalidate)
    }

    private fun setDefaultLoginErrorState() {
        _authState.value = AuthState.SingInLoginError(ErrorLoginSubState.Default)
    }

    private fun setDefaultPasswordErrorState() {
        _authState.value = AuthState.SingInPasswordError(ErrorPasswordSubState.Default)
    }
}
