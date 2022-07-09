package com.example.sass.domain

import javax.inject.Inject

class SingInUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(login: String, password: String) {
        userRepository.signIn(login, password)
    }
}