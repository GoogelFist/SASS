package com.example.sass.domain.usecases

import com.example.sass.domain.PicturesRepository
import com.example.sass.domain.UserRepository
import javax.inject.Inject

class ClearUserDataUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val picturesRepository: PicturesRepository
) {
    suspend operator fun invoke() {
        userRepository.clearData()
        picturesRepository.clearData()
    }
}