package com.example.sass.domain

interface Repository {
    suspend fun signIn(phone: String, password: String)
}