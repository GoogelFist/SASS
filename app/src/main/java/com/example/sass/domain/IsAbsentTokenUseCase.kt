package com.example.sass.domain

import javax.inject.Inject

class IsAbsentTokenUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(): Boolean {
        return userRepository.isAbsentToken()
    }
}