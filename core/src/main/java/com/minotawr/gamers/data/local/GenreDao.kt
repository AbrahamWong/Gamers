package com.minotawr.gamers.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.minotawr.gamers.data.local.entity.GenreEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GenreDao {

    // Rating
    @Query("SELECT * FROM genre")
    fun selectAllGenres(): Flow<List<GenreEntity>>

    @Query("SELECT * FROM genre WHERE genreId = :id")
    fun selectGenre(id: Int): Flow<GenreEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGenreList(ratings: List<GenreEntity>): LongArray

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGenre(rating: GenreEntity): Long

    @Query("DELETE FROM genre")
    suspend fun deleteGenres()

}