package com.example.sass.data.datasource.local.picture

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sass.data.datasource.local.picture.models.PictureAndFavoriteDbEntity
import com.example.sass.data.datasource.local.picture.models.PictureDbEntity

@Dao
interface PicturesDao {

    @Query("SELECT * FROM pictures ")
    suspend fun loadPictures(): List<PictureAndFavoriteDbEntity>

    @Query(
        "SELECT * FROM pictures " +
                "LEFT JOIN favorites ON Pictures.picture_id = Favorites.favorite_id " +
                "WHERE is_favorite = 1 " +
                "ORDER BY added_date DESC"
    )
    suspend fun loadFavoritePictures(): List<PictureAndFavoriteDbEntity>

    @Query("SELECT * FROM pictures WHERE title LIKE '%' || :searchText || '%' ")
    suspend fun findPictures(searchText: String): List<PictureAndFavoriteDbEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePicture(pictureDbEntity: PictureDbEntity)

    @Query("SELECT * FROM pictures WHERE picture_id = :id")
    suspend fun getPictureById(id: String): PictureDbEntity

    @Query("DELETE FROM pictures")
    suspend fun deleteAllPictures()

    @Query(
        "INSERT OR REPLACE INTO favorites (favorite_id, added_date, is_favorite) " +
                "VALUES (:id, STRFTIME('%s', 'now') * 1000, 1)"
    )
    suspend fun addPictureToFavorite(id: String)

    @Query("DELETE FROM favorites WHERE favorite_id = :id")
    suspend fun removePictureFromFavorite(id: String)

    @Query("DELETE FROM favorites")
    suspend fun deleteAllPicturesFromFavorites()
}