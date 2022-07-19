package com.example.sass.domain.usecases

import com.example.sass.domain.UserRepository
import com.example.sass.domain.models.UserInfo
import javax.inject.Inject

class LoadUserInfoUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(): UserInfo {
        return userRepository.loadUserInfo()
    }
}