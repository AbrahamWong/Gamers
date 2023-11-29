package com.minotawr.gamers.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game")
data class GameEntity(
    @PrimaryKey(autoGenerate = true)
    val gameId: Long = 0,
    val id: Int,
    val slug: String?,
    val name: String?,
    val nameOriginal: String?,
    val description: String?,
    val descriptionRaw: String?,
    val metacritic: Int?,
    val metacriticUrl: String?,
    val released: String?,
    val tba: Boolean?,
    val backgroundImage: String?,
    val backgroundImageAdditional: String?,
    val website: String?,
    val rating: Double?,
    val ratingTop: Int?,
    val ratingsCount: Int?,
    val dominantColor: String?,
    val page: Int,
)
