package com.example.sass.domain

import com.example.sass.domain.models.PicturesItem

interface PicturesRepository {

    suspend fun getPictures(): List<PicturesItem>
}