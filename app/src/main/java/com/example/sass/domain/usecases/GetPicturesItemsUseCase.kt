package com.example.sass.domain.usecases

import com.example.sass.domain.PicturesRepository
import com.example.sass.domain.models.PicturesItem
import javax.inject.Inject

class GetPicturesItemsUseCase @Inject constructor(private val picturesRepository: PicturesRepository) {
    suspend operator fun invoke(): List<PicturesItem> {
        return picturesRepository.getPicturesItems()
    }
}