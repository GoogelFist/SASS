package com.example.sass.domain.usecases

import com.example.sass.domain.PicturesRepository
import com.example.sass.domain.models.PicturesItem
import javax.inject.Inject

class SearchPicturesItemsUseCase @Inject constructor(private val picturesRepository: PicturesRepository) {
    suspend operator fun invoke(text: String): List<PicturesItem> {
        return picturesRepository.getPicturesItems().filter { it.title.lowercase().contains(text.lowercase()) }
    }
}