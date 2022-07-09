package com.example.sass.domain

interface UserRepository {
    suspend fun signIn(phone: String, password: String)

    suspend fun isAbsentToken(): Boolean
}