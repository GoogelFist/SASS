package com.example.sass.data.datasource.local.picture

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sass.data.datasource.local.picture.models.PictureDao

@Dao
interface PicturesDao {

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