package com.minotawr.favoritegame.di

import com.minotawr.favoritegame.ui.FavoriteViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoriteViewModelModule = module {
    viewModel { FavoriteViewModel(get()) }
}