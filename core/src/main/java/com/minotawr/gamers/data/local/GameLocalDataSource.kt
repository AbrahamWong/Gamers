package com.minotawr.gamers.data.local

import com.minotawr.gamers.data.local.entity.GameEntity
import com.minotawr.gamers.data.local.entity.GameGenreCrossRef
import com.minotawr.gamers.data.local.entity.GameTagsCrossRef
import com.minotawr.gamers.data.local.entity.GenreEntity
import com.minotawr.gamers.data.local.entity.TagsEntity

class GameLocalDataSource(
    private val gamesDao: GamesDao,
    private val genreDao: GenreDao,
    private val tagsDao: TagsDao,
) {

    // Games
    fun getAllGames() = gamesDao.selectAllGames()

    fun selectGame(id: Int) = gamesDao.selectGame(id)

    suspend fun insertGame(game: GameEntity) =
        gamesDao.insertGame(game)

    suspend fun deleteGames(id: Int) = gamesDao.deleteGames(id)

    // Genre
    fun getAllGenres() = genreDao.selectAllGenres()

    fun selectGenre(id: Int) = genreDao.selectGenre(id)

    suspend fun insertGenreList(genres: List<GenreEntity>) =
        genreDao.insertGenreList(genres)

    suspend fun insertGenre(genre: GenreEntity) =
        genreDao.insertGenre(genre)

    suspend fun deleteGenres() = genreDao.deleteGenres()

    // Tags
    suspend fun insertTagsList(tagList: List<TagsEntity>) =
        tagsDao.insertTagsList(tagList)

    // Cross-ref
    suspend fun insertGameGenreCrossRef(crf: GameGenreCrossRef) =
        gamesDao.insertGameGenreCrossRef(crf)

    suspend fun deleteGameGenreCrossRef(gameId: Long) = gamesDao.deleteGameGenreCrossRef(gameId)

    suspend fun insertGameTagsCrossRef(crf: GameTagsCrossRef) =
        gamesDao.insertGameTagsCrossRef(crf)

    suspend fun deleteGameTagsCrossRef(gameId: Long) = gamesDao.deleteGameTagsCrossRef(gameId)
}