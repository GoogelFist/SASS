package com.example.sass.domain.usecases

import com.example.sass.domain.PicturesRepository
import com.example.sass.domain.models.FavoritePicturesItem
import javax.inject.Inject

class GetFavoritesPicturesItemsUseCase @Inject constructor(private val picturesRepository: PicturesRepository) {
    suspend operator fun invoke(): List<FavoritePicturesItem> {
        return picturesRepository.getFavoritePicturesItems()
    }
}