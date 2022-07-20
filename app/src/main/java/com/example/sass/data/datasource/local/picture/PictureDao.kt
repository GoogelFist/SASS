package com.example.sass.data.datasource.local.picture

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sass.data.datasource.local.picture.models.FavoritePicDao
import com.example.sass.data.datasource.local.picture.models.PicCommonDao

@Dao
interface PictureDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFavoritePicDao(favoritePicDao: FavoritePicDao)

    @Query("SELECT * FROM favorite_pictures ORDER BY added_time_mills DESC")
    suspend fun loadFavoritePicsDao(): List<FavoritePicDao>

    @Query("SELECT id FROM favorite_pictures")
    suspend fun loadFavoritePicsDaoIds(): List<String>

    @Query("DELETE FROM favorite_pictures WHERE id = :id")
    suspend fun deleteFavoritePicDao(id: String)

    @Query("DELETE FROM favorite_pictures")
    suspend fun deleteAllFavoritePics()


    @Query("SELECT * FROM pictures_common")
    suspend fun loadPicsCommonDao(): List<PicCommonDao>

    @Query("SELECT * FROM pictures_common WHERE title LIKE '%' || :searchText || '%' ")
    suspend fun searchPicsCommonDao(searchText: String): List<PicCommonDao>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePicCommonDao(picCommonDao: PicCommonDao)

    @Query("SELECT * FROM pictures_common WHERE picture_id = :id")
    suspend fun getPicCommonDaoById(id: String): PicCommonDao

    @Query("DELETE FROM pictures_common")
    suspend fun deleteAllPicsCommonDao()
}