package com.example.sass.domain.usecases

import com.example.sass.domain.UserRepository
import javax.inject.Inject

class IsAbsentTokenUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(): Boolean {
        return userRepository.isAbsentToken()
    }
}