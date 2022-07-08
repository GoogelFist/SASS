package com.example.sass.domain

import javax.inject.Inject

class SingInUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(login: String, password: String) {
        repository.signIn(login, password)
    }
}