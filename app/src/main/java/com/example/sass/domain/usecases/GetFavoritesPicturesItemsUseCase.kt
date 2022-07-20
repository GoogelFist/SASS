package com.example.sass.domain.usecases

import com.example.sass.domain.PicturesRepository
import com.example.sass.domain.models.FavoritePictureItem
import javax.inject.Inject

class GetFavoritesPicturesItemsUseCase @Inject constructor(private val picturesRepository: PicturesRepository) {
    suspend operator fun invoke(): List<FavoritePictureItem> {
        return picturesRepository.getFavoritePicsItems()
    }
}