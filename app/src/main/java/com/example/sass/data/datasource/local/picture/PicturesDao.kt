package com.example.sass.data.datasource.local.picture

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sass.data.datasource.local.picture.models.FavoritePictureDao
import com.example.sass.data.datasource.local.picture.models.PictureDao

@Dao
interface PicturesDao {

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


    @Query("SELECT * FROM pictures")
    suspend fun loadPicturesDao(): List<PictureDao>

    @Query("SELECT * FROM pictures WHERE title LIKE '%' || :searchText || '%' ")
    suspend fun findPicturesDao(searchText: String): List<PictureDao>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePictureDao(pictureDao: PictureDao)

    @Query("SELECT * FROM pictures WHERE picture_id = :id")
    suspend fun getPictureDaoById(id: String): PictureDao

    @Query("DELETE FROM pictures")
    suspend fun deleteAllPicturesDao()
}