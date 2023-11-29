package com.minotawr.gamers.domain.usecase

import com.minotawr.gamers.domain.model.Game
import com.minotawr.gamers.domain.repository.IGameRepository

class GameInteractor(
    private val repository: IGameRepository
): GameUseCase {

    override fun getGameList(page: Int) = repository.getGameList(page)

    override fun setGameAsFavorite(game: Game) = repository.setGameAsFavorite(game)

    override fun deleteGameFromFavorite(game: Game) = repository.deleteGameFromFavorite(game)

    override fun getFavoriteGameList() = repository.getFavoriteGameList()

    override fun getGameDetail(id: Int) = repository.getGameDetail(id)

}