package com.example.sass.presentation.screens.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sass.domain.usecases.ClearUserDataUseCase
import com.example.sass.domain.usecases.SingInUseCase
import com.example.sass.presentation.screens.EventHandler
import com.example.sass.presentation.screens.auth.models.AuthEvent
import com.example.sass.presentation.screens.auth.models.AuthState
import com.example.sass.presentation.screens.auth.models.ErrorLoginSubState
import com.example.sass.presentation.screens.auth.models.ErrorPasswordSubState
import kotlinx.coroutines.launch

class AuthViewModel(
    private val singInUseCase: SingInUseCase,
    private val clearUserDataUseCase: ClearUserDataUseCase
) : ViewModel(),
    EventHandler<AuthEvent> {

    private var _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState>
        get() = _authState

    override fun obtainEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.OnSignIn -> signedIn(event.login, event.password)
            AuthEvent.OnClearUserData -> clearedUserData()
        }
    }

    private fun signedIn(login: String, password: String) {

        val isValidate = validate(login, password)

        if (isValidate) {
            val formattedLogin = AuthStringUtils.formatPhone(login)

            viewModelScope.launch {
                try {
                    _authState.value = AuthState.SigningIn

                    singInUseCase(formattedLogin, password)
                    _authState.value = AuthState.SignedIn
                } catch (error: Throwable) {
                    _authState.value = AuthState.SingInError
                }
            }
        }
    }

    private fun clearedUserData() {
        viewModelScope.launch {
            clearUserDataUseCase()
        }
    }

    private fun validate(login: String, password: String): Boolean {
        var validateErrState = AuthState.SingInValidateError()

        val loginErrSubState = AuthValidator.validateLogin(login)
        val passwordErrSubState = AuthValidator.validatePassword(password)

        if (loginErrSubState != ErrorLoginSubState.Default) {
            validateErrState = validateErrState.copy(loginErrorSubState = loginErrSubState)
        }
        if (passwordErrSubState != ErrorPasswordSubState.Default) {
            validateErrState = validateErrState.copy(passwordErrorSubState = passwordErrSubState)
        }

        _authState.value = validateErrState

        val isLoginDefaultSubState = loginErrSubState == ErrorLoginSubState.Default
        val isPasswordDefaultSubState = passwordErrSubState == ErrorPasswordSubState.Default

        return isLoginDefaultSubState && isPasswordDefaultSubState
    }
}
