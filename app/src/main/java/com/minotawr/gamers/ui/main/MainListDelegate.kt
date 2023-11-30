package com.minotawr.gamers.ui.main

import com.minotawr.gamers.domain.model.Game

interface MainListDelegate {
    fun onItemClick(item: Game)

    fun onFavorite(item: Game)
}