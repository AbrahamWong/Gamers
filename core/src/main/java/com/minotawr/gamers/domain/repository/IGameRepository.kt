package com.minotawr.gamers.domain.repository

import com.minotawr.gamers.data.base.Result
import com.minotawr.gamers.domain.model.Game
import kotlinx.coroutines.flow.Flow

interface IGameRepository {

    fun getGameList(page: Int): Flow<Result<List<Game>?>>

    fun getFavoriteGameList(): Flow<Result<List<Game>?>>

    fun setGameAsFavorite(game: Game): Flow<Result<Any>>

    fun deleteGameFromFavorite(game: Game): Flow<Result<Any>>

    fun getGameDetail(id: Int): Flow<Result<Game>>

}