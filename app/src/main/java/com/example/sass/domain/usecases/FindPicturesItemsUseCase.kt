package com.example.sass.domain.usecases

import com.example.sass.domain.PicturesRepository
import com.example.sass.domain.models.PicturesItem
import javax.inject.Inject

class FindPicturesItemsUseCase @Inject constructor(private val picturesRepository: PicturesRepository) {
    suspend operator fun invoke(searchText: String): List<PicturesItem> {
        return picturesRepository.findPicturesItems(searchText)
    }
}