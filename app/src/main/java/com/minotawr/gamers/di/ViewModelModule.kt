package com.minotawr.gamers.di

import com.minotawr.gamers.ui.detail.GameDetailViewModel
import com.minotawr.gamers.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get()) }

    viewModel { GameDetailViewModel(get()) }
}