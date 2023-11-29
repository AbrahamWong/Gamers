package com.minotawr.gamers.utils

import com.minotawr.gamers.data.local.entity.GameEntity
import com.minotawr.gamers.data.local.entity.GameFullEntity
import com.minotawr.gamers.data.local.entity.GenreEntity
import com.minotawr.gamers.data.local.entity.TagsEntity
import com.minotawr.gamers.data.remote.response.GameResponse
import com.minotawr.gamers.data.remote.response.GenreResponse
import com.minotawr.gamers.data.remote.response.TagsResponse
import com.minotawr.gamers.domain.model.Game
import com.minotawr.gamers.domain.model.Genre
import com.minotawr.gamers.domain.model.Tag

object DataGameMapper {

    fun mapGameEntityToModel(entity: GameFullEntity) =
        entity.run {
            Game(
                id = game.id,
                slug = game.slug,
                name = game.name,
                nameOriginal = game.nameOriginal,
                description = game.description,
                descriptionRaw = game.descriptionRaw,
                metacritic = game.metacritic,
                metacriticUrl = game.metacriticUrl,
                released = game.released,
                tba = game.tba,
                backgroundImage = game.backgroundImage,
                backgroundImageAdditional = game.backgroundImageAdditional,
                website = game.website,
                rating = game.rating,
                ratingTop = game.ratingTop,
                ratingsCount = game.ratingsCount,
                dominantColor = game.dominantColor,
                genres = genres.map { mapGenreEntityToModel(it) },
                tags = tags.map { mapTagsEntityToModel(it) },
                isFavorite = true,
                page = game.page
            )
        }

    fun mapGameModelToEntity(model: Game) =
        model.run {
            GameEntity(
                id = id ?: 0,
                slug = slug,
                name = name,
                nameOriginal = nameOriginal,
                description = description,
                descriptionRaw = descriptionRaw,
                metacritic = metacritic,
                metacriticUrl = metacriticUrl,
                released = released,
                tba = tba,
                backgroundImage = backgroundImage,
                backgroundImageAdditional = backgroundImageAdditional,
                website = website,
                rating = rating,
                ratingTop = ratingTop,
                ratingsCount = ratingsCount,
                dominantColor = dominantColor,
                page = page,
            )
        }

    fun mapGameResponseToModel(response: GameResponse, gameIdList: List<Int>, page: Int) =
        response.run {
            Game(
                id = id,
                slug = slug,
                name = name,
                nameOriginal = nameOriginal,
                description = description,
                descriptionRaw = descriptionRaw,
                metacritic = metacritic,
                metacriticUrl = metacriticUrl,
                released = released,
                tba = tba,
                backgroundImage = backgroundImage,
                backgroundImageAdditional = backgroundImageAdditional,
                website = website,
                rating = rating,
                ratingTop = ratingTop,
                ratingsCount = ratingsCount,
                dominantColor = dominantColor,
                tags = tags?.map { mapTagResponseToModel(it) },
                genres = genres?.map { mapGenreResponseToModel(it) },
                isFavorite = gameIdList.contains(id),
                page = page,
            )
        }

    fun mapGenreEntityToModel(entity: GenreEntity) =
        entity.run {
            Genre(
                genreId.toInt(),
                name,
                gamesCount,
                imageBackground,
                slug,
            )
        }

    fun mapGenreModelToEntity(model: Genre) =
        model.run {
            GenreEntity(
                genreId = id.toLong(),
                name = name,
                imageBackground = imageBackground,
                gamesCount = gamesCount,
                slug = slug,
            )
        }

    fun mapGenreResponseToModel(response: GenreResponse) =
        response.run {
            Genre(
                id = id ?: 0,
                name = name,
                gamesCount = gamesCount,
                imageBackground = imageBackground,
                slug = slug,
            )
        }

    fun mapTagResponseToModel(response: TagsResponse) =
        response.run {
            Tag(
                id = id ?: 0,
                name = name,
                slug = slug,
                imageBackground = imageBackground,
                language = language,
                gamesCount = gamesCount,
            )
        }

    fun mapTagsEntityToModel(entity: TagsEntity) =
        entity.run {
            Tag(
                id = tagId.toInt(),
                name = name,
                slug = slug,
                imageBackground = imageBackground,
                language = language,
                gamesCount = gamesCount,
            )
        }

    fun mapTagsModelToEntity(model: Tag) =
        model.run {
            TagsEntity(
                tagId = id.toLong(),
                name = name,
                slug = slug,
                imageBackground = imageBackground,
                language = language,
                gamesCount = gamesCount,
            )
        }
}