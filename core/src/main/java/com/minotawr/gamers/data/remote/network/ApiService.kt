package com.minotawr.gamers.data.remote.network

import com.minotawr.gamers.data.remote.response.GameListResponse
import com.minotawr.gamers.data.remote.response.GameResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // Game
    @GET("games")
    suspend fun getAllGames(
        @Query("key") apiKey: String,
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int
    ): Response<GameListResponse>

    @GET("games/{id}")
    suspend fun getGameDetail(
        @Path("id") id: Int,
        @Query("key") apiKey: String,
    ): Response<GameResponse>
}