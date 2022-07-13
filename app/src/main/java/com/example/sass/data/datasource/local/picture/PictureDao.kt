package com.example.sass.data.datasource.local.picture

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sass.data.datasource.local.picture.models.PictureFavoriteDao

@Dao
interface PictureDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePictureFavorite(pictureFavoriteDao: PictureFavoriteDao)

    @Query("DELETE FROM pictures_favorites WHERE favorite_id = :id")
    suspend fun deletePictureFavorite(id: String)

    @Query("SELECT * FROM pictures_favorites ORDER BY added_time_mills DESC")
    suspend fun loadPicturesFavorites(): List<PictureFavoriteDao>

    @Query("DELETE FROM pictures_favorites")
    suspend fun deleteAll()
}