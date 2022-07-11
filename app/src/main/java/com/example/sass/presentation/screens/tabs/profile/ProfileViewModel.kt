package com.example.sass.presentation.screens.tabs.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sass.data.user.IncorrectTokenException
import com.example.sass.domain.LoadUserInfoUseCase
import com.example.sass.domain.SingOutUseCase
import com.example.sass.domain.models.UserInfo
import com.example.sass.presentation.screens.EventHandler
import com.example.sass.presentation.screens.tabs.profile.models.ProfileEvent
import com.example.sass.presentation.screens.tabs.profile.models.ProfileState
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val singOutUseCase: SingOutUseCase,
    private val loadUserInfoUseCase: LoadUserInfoUseCase
) : ViewModel(),
    EventHandler<ProfileEvent> {

    private var _profileState = MutableLiveData<ProfileState>()
    val profileState: LiveData<ProfileState>
        get() = _profileState

    private var _userInfo = MutableLiveData<UserInfo>()
    val userInfo: LiveData<UserInfo>
        get() = _userInfo

    override fun obtainEvent(event: ProfileEvent) {
        when (event) {
            ProfileEvent.OnDefaultState -> setDefaultState()
            ProfileEvent.OnSignOut -> signOut()
            ProfileEvent.OnLoadUserInfo -> loadUserInfo()
        }
    }

    private fun loadUserInfo() {
        viewModelScope.launch {
            val userInfo = loadUserInfoUseCase()
            _userInfo.value = userInfo
        }
    }

    private fun setDefaultState() {
        _profileState.value = ProfileState.Default
    }

    private fun signOut() {
        viewModelScope.launch {
            try {
                setDefaultState()
                _profileState.value = ProfileState.SigningOutState
                singOutUseCase()
                _profileState.value = ProfileState.SignedOutState

            } catch (error: Throwable) {
                when (error) {
                    is IncorrectTokenException -> {
                        _profileState.value = ProfileState.IncorrectTokenState
                    }
                    else -> {
                        _profileState.value = ProfileState.SingOutErrorState
                    }
                }
            }
        }
    }
}
