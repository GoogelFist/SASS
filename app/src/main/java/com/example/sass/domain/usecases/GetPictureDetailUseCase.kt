package com.example.sass.domain.usecases

import com.example.sass.domain.PicturesRepository
import com.example.sass.domain.models.PictureDetail
import javax.inject.Inject

class GetPictureDetailUseCase @Inject constructor(private val picturesRepository: PicturesRepository) {
    suspend operator fun invoke(id: String): PictureDetail {
        return picturesRepository.getPictureDetail(id)
    }
}