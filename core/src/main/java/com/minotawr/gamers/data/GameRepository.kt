package com.minotawr.gamers.data

import com.minotawr.gamers.data.base.NetworkBoundProcessResource
import com.minotawr.gamers.data.base.Result
import com.minotawr.gamers.data.local.AuthLocalDataSource
import com.minotawr.gamers.data.local.GameLocalDataSource
import com.minotawr.gamers.data.local.entity.GameGenreCrossRef
import com.minotawr.gamers.data.local.entity.GameTagsCrossRef
import com.minotawr.gamers.data.remote.GameRemoteDataSource
import com.minotawr.gamers.data.remote.response.GameListResponse
import com.minotawr.gamers.data.remote.response.GameResponse
import com.minotawr.gamers.domain.model.Game
import com.minotawr.gamers.domain.repository.IGameRepository
import com.minotawr.gamers.utils.DataGameMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class GameRepository(
    private val authLocalDataSource: AuthLocalDataSource,
    private val gameRemoteDataSource: GameRemoteDataSource,
    private val gameLocalDataSource: GameLocalDataSource,
) : IGameRepository {

    override fun getGameList(page: Int): Flow<Result<List<Game>?>> =
        object : NetworkBoundProcessResource<List<Game>?, GameListResponse>(
            authLocalDataSource
        ) {
            override suspend fun fetchData(): Result<GameListResponse> =
                gameRemoteDataSource.getAllGames(page)

            override suspend fun callbackResponse(remoteData: GameListResponse): List<Game>? {
                val gameIdList = gameLocalDataSource.getAllGames().first().map { it.game.id }

                return remoteData.results?.map {
                    DataGameMapper.mapGameResponseToModel(it, gameIdList, page)
                }
            }
        }.asFlow()

    override fun getFavoriteGameList(): Flow<Result<List<Game>?>> = flow {
        emit(Result.Loading())

        val favoriteEntity = gameLocalDataSource.getAllGames()
        val favoriteGame = favoriteEntity.map {
            it.map { ent ->
                DataGameMapper.mapGameEntityToModel(
                    ent
                )
            }
        }

        emit(Result.Success(favoriteGame.first()))
    }

    override fun setGameAsFavorite(game: Game): Flow<Result<Any>> = flow {
        emit(Result.Loading())

        val gameEntity = DataGameMapper.mapGameModelToEntity(game)
        val gameId = gameLocalDataSource.insertGame(gameEntity)

        val genreListEntities = game.genres?.map { DataGameMapper.mapGenreModelToEntity(it) }
        if (!genreListEntities.isNullOrEmpty())
            gameLocalDataSource.insertGenreList(genreListEntities)

        genreListEntities?.map { it.genreId }?.forEach { genreId ->
            gameLocalDataSource.insertGameGenreCrossRef(
                GameGenreCrossRef(gameId, genreId)
            )
        }

        val tagsListEntities = game.tags?.map { DataGameMapper.mapTagsModelToEntity(it) }
        if (!tagsListEntities.isNullOrEmpty())
            gameLocalDataSource.insertTagsList(tagsListEntities)

        tagsListEntities?.map { it.tagId }?.forEach { tagId ->
            gameLocalDataSource.insertGameTagsCrossRef(
                GameTagsCrossRef(gameId, tagId)
            )
        }

        emit(Result.Success(null))
    }

    override fun deleteGameFromFavorite(game: Game): Flow<Result<Any>> = flow {
        if (game.id == null) {
            emit(Result.Failed(null))
            return@flow
        }

        val gameEntity = gameLocalDataSource.selectGame(game.id).first()
        val id = gameEntity.game.id
        val gameId = gameEntity.game.gameId

        gameLocalDataSource.deleteGameGenreCrossRef(gameId)
        gameLocalDataSource.deleteGameTagsCrossRef(gameId)

        gameLocalDataSource.deleteGames(id)

        emit(Result.Success(null))
    }

    override fun getGameDetail(id: Int) =
        object: NetworkBoundProcessResource<Game, GameResponse>(authLocalDataSource) {
            override suspend fun fetchData() = gameRemoteDataSource.getGameDetail(id)

            override suspend fun callbackResponse(remoteData: GameResponse): Game {
                val gameIdList = gameLocalDataSource.getAllGames().first().map { it.game.id }
                return DataGameMapper.mapGameResponseToModel(remoteData, gameIdList, 0)
            }

        }.asFlow()
}