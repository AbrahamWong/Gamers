package com.minotawr.gamers.data.remote

import com.minotawr.gamers.data.remote.network.ApiService

class GameRemoteDataSource(
    private val apiService: ApiService
): BaseRemoteDataSource() {

    suspend fun getAllGames(page: Int, pageSize: Int = 10) = getResult { apiService.getAllGames(apiKey, page, pageSize) }

    suspend fun getGameDetail(id: Int) = getResult { apiService.getGameDetail(id, apiKey) }

}