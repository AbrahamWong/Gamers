package com.minotawr.gamers.domain.usecase

import com.minotawr.gamers.data.base.Result
import com.minotawr.gamers.domain.model.Game
import kotlinx.coroutines.flow.Flow

interface GameUseCase {

    fun getGameList(page: Int): Flow<Result<List<Game>?>>

    fun setGameAsFavorite(game: Game): Flow<Result<Any>>

    fun deleteGameFromFavorite(game: Game): Flow<Result<Any>>

    fun getFavoriteGameList(): Flow<Result<List<Game>?>>

    fun getGameDetail(id: Int): Flow<Result<Game>>

}