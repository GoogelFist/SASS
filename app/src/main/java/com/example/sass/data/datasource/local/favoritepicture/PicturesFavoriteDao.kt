package com.example.sass.data.datasource.local.favoritepicture

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sass.data.datasource.local.favoritepicture.models.FavoritePictureDao

@Dao
interface PicturesFavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFavoritePictureDao(favoritePictureDao: FavoritePictureDao)

    @Query("SELECT * FROM favorite_pictures ORDER BY added_time_mills DESC")
    suspend fun loadFavoritePicturesDao(): List<FavoritePictureDao>

    @Query("SELECT id FROM favorite_pictures")
    suspend fun loadFavoritePicturesDaoIds(): List<String>

    @Query("DELETE FROM favorite_pictures WHERE id = :id")
    suspend fun deleteFavoritePictureDao(id: String)

    @Query("DELETE FROM favorite_pictures")
    suspend fun deleteAllFavoritePicturesDao()
}