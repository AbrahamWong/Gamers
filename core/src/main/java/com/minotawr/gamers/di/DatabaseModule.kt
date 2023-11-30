package com.minotawr.gamers.di

import android.content.Context
import androidx.room.Room
import com.minotawr.gamers.core.BuildConfig
import com.minotawr.gamers.data.local.GamersDatabase
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single { provideDatabase(androidContext()) }

    single { get<GamersDatabase>().gamesDao() }
    single { get<GamersDatabase>().genreDao() }
    single { get<GamersDatabase>().tagsDao() }
}

fun provideDatabase(context: Context): GamersDatabase {
    val passphrase: ByteArray = SQLiteDatabase.getBytes(BuildConfig.DB_PASSPHRASE.toCharArray())
    val factory = SupportFactory(passphrase)
    return Room.databaseBuilder(
        context.applicationContext,
        GamersDatabase::class.java,
        "Gamers.db"
    ).fallbackToDestructiveMigration()
        .openHelperFactory(factory)
        .build()
}