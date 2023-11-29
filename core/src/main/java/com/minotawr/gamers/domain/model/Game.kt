package com.minotawr.gamers.domain.model

data class Game(
    val id: Int?,
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

    val tags: List<Tag>?,
    val genres: List<Genre>?,

    var isFavorite: Boolean,
    val page: Int,
)
