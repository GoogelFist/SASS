package com.example.sass.domain.usecases

import com.example.sass.domain.UserRepository
import javax.inject.Inject

class SingOutUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke() {
        userRepository.signOut()
    }
}