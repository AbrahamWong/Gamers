package com.minotawr.gamers.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.minotawr.gamers.data.GameRepository
import com.minotawr.gamers.data.local.AuthLocalDataSource
import com.minotawr.gamers.data.local.GameLocalDataSource
import com.minotawr.gamers.data.remote.GameRemoteDataSource
import com.minotawr.gamers.domain.repository.IGameRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    // auth
    single { AuthLocalDataSource(androidContext().dataStore) }

    // game
    single { GameLocalDataSource(get(), get(), get()) }
    single { GameRemoteDataSource(get()) }

    single<IGameRepository> { GameRepository(get(), get(), get()) }
}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = AuthLocalDataSource.DATA_STORE)