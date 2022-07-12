package com.example.sass.domain

import javax.inject.Inject

class LoadPicturesItemsUseCase @Inject constructor(private val picturesRepository: PicturesRepository) {
    suspend operator fun invoke() {
        return picturesRepository.loadPictures()
    }
}