package com.minotawr.favoritegame.ui

import com.minotawr.gamers.domain.model.Game

interface FavoriteListDelegate {
    fun onItemClick(item: Game)
}