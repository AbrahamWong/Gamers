package com.minotawr.gamers.ui.main

import androidx.lifecycle.asLiveData
import com.minotawr.gamers.domain.usecase.GameUseCase
import com.minotawr.gamers.ui.base.GamersViewModel

class MainViewModel(
    useCase: GameUseCase,
) : GamersViewModel(useCase) {

    var page = 1

    fun getGameList(page: Int) = useCase.getGameList(page).asLiveData()

}