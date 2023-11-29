package com.minotawr.gamers.di

import com.minotawr.gamers.domain.usecase.GameInteractor
import com.minotawr.gamers.domain.usecase.GameUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory<GameUseCase> {
        GameInteractor(get())
    }
}