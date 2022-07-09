package com.example.sass.domain

import javax.inject.Inject

class SingOutUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke() {
        userRepository.signOut()
    }
}