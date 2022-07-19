package com.example.sass.domain.usecases

import com.example.sass.domain.PicturesRepository
import javax.inject.Inject

class LoadPicturesItemsUseCase @Inject constructor(private val picturesRepository: PicturesRepository) {
    suspend operator fun invoke() {
        return picturesRepository.loadPictures()
    }
}