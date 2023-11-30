package com.minotawr.gamers.di

import com.minotawr.gamers.data.GameRepository
import com.minotawr.gamers.data.local.GameLocalDataSource
import com.minotawr.gamers.data.remote.GameRemoteDataSource
import com.minotawr.gamers.domain.repository.IGameRepository
import org.koin.dsl.module

val repositoryModule = module {
    // game
    single { GameLocalDataSource(get(), get(), get()) }
    single { GameRemoteDataSource(get()) }

    single<IGameRepository> { GameRepository(get(), get()) }
}