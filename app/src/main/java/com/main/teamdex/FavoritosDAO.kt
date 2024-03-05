package com.main.teamdex

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface FavoritosDAO {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertFavorito(favorito: FavoritosEntity)

    @Delete
    fun deleteFavorito(favorito: FavoritosEntity)

    @Update
    fun update(favorito: FavoritosEntity)

    @Query("SELECT * FROM favoritos")
    fun getAllFav(): List<FavoritosEntity>?
}