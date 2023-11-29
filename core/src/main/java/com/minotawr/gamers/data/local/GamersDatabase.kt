package com.minotawr.gamers.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.minotawr.gamers.data.local.entity.GameEntity
import com.minotawr.gamers.data.local.entity.GameGenreCrossRef
import com.minotawr.gamers.data.local.entity.GameTagsCrossRef
import com.minotawr.gamers.data.local.entity.GenreEntity
import com.minotawr.gamers.data.local.entity.TagsEntity

@Database(
    entities = [GameEntity::class, GenreEntity::class, TagsEntity::class, GameGenreCrossRef::class, GameTagsCrossRef::class],
    version = 1,
    exportSchema = false,
)
abstract class GamersDatabase : RoomDatabase() {

    abstract fun gamesDao(): GamesDao
    abstract fun genreDao(): GenreDao
    abstract fun tagsDao(): TagsDao

}