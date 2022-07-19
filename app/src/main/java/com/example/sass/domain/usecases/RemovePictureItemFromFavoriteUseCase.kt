package com.example.sass.domain.usecases

import com.example.sass.domain.PicturesRepository
import javax.inject.Inject

class RemovePictureItemFromFavoriteUseCase @Inject constructor(private val picturesRepository: PicturesRepository) {
    suspend operator fun invoke(id: String) {
        return picturesRepository.removedFromFavorite(id)
    }
}