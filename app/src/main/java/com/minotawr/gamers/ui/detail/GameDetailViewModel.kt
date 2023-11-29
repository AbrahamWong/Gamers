package com.minotawr.gamers.ui.detail

import androidx.lifecycle.asLiveData
import com.minotawr.gamers.domain.model.Game
import com.minotawr.gamers.domain.usecase.GameUseCase
import com.minotawr.gamers.ui.base.GamersViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class GameDetailViewModel(gameUseCase: GameUseCase): GamersViewModel(gameUseCase) {

    val isFavorite = MutableStateFlow<Boolean>(false)
    var game: Game? = null

    fun getGame(id: Int) = useCase.getGameDetail(id).asLiveData()

}