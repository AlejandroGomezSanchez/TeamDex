package com.main.teamdex

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("favoritos")
data class FavoritosEntity(
    @PrimaryKey()
    val id : Int
)