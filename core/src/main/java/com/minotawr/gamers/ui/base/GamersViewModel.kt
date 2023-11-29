package com.minotawr.gamers.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.minotawr.gamers.domain.model.Game
import com.minotawr.gamers.domain.usecase.GameUseCase

abstract class GamersViewModel(protected val useCase: GameUseCase): ViewModel() {

    fun setGameAsFavorite(game: Game) = useCase.setGameAsFavorite(game).asLiveData()

    fun deleteGameFromFavorite(game: Game) = useCase.deleteGameFromFavorite(game).asLiveData()

}