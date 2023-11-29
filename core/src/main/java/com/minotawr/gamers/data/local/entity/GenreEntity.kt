package com.minotawr.gamers.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genre")
data class GenreEntity(
    @PrimaryKey
    val genreId: Long,
    val name: String?,
    val imageBackground: String?,
    val gamesCount: Int?,
    val slug: String?,
)
