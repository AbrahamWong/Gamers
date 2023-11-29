package com.minotawr.gamers.domain.model

data class Tag(
    val id: Int,
    val name: String?,
    val slug: String?,
    val imageBackground: String?,
    val language: String?,
    val gamesCount: Int?,
)
