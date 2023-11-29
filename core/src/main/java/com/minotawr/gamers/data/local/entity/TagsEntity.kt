package com.minotawr.gamers.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tag")
data class TagsEntity(
    @PrimaryKey
    val tagId: Long = 0,
    val name: String?,
    val slug: String?,
    val imageBackground: String?,
    val language: String?,
    val gamesCount: Int?,
)
