package com.example.sass.domain

import javax.inject.Inject

class AddPictureItemToFavoriteUseCase @Inject constructor(private val picturesRepository: PicturesRepository) {
    suspend operator fun invoke(id: String) {
        return picturesRepository.addToFavorite(id)
    }
}