package com.example.hskmaster.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.hskmaster.data.local.entity.FavoriteEntity

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavoriteEntity)

    @Query("DELETE FROM favorite_vocab WHERE id = :id")
    suspend fun deleteFavoriteById(id: String)

    @Query("SELECT * FROM favorite_vocab")
    suspend fun getAllFavorites(): List<FavoriteEntity>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_vocab WHERE id = :id)")
    suspend fun isFavorite(id: String): Boolean
}

