package com.minotawr.gamers

import android.app.Application
import com.minotawr.gamers.di.databaseModule
import com.minotawr.gamers.di.networkModule
import com.minotawr.gamers.di.repositoryModule
import com.minotawr.gamers.di.useCaseModule
import com.minotawr.gamers.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MayApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MayApplication)
            androidLogger(Level.NONE)
            modules(
                databaseModule,
                networkModule,
                repositoryModule,
                useCaseModule,
                viewModelModule,
            )
        }
    }
}