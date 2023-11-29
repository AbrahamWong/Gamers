package com.minotawr.favoritegame.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.minotawr.gamers.domain.usecase.GameUseCase
import com.minotawr.gamers.ui.base.GamersViewModel

class FavoriteViewModel(
    useCase: GameUseCase,
) : GamersViewModel(useCase) {

    fun getFavoriteGame() = useCase.getFavoriteGameList().asLiveData()

}