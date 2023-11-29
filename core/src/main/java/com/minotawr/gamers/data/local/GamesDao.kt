package com.minotawr.gamers.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.minotawr.gamers.data.local.entity.GameEntity
import com.minotawr.gamers.data.local.entity.GameFullEntity
import com.minotawr.gamers.data.local.entity.GameGenreCrossRef
import com.minotawr.gamers.data.local.entity.GameTagsCrossRef
import kotlinx.coroutines.flow.Flow

@Dao
interface GamesDao {

    // Games
    @Transaction
    @Query("SELECT * FROM game")
    fun selectAllGames(): Flow<List<GameFullEntity>>

    @Transaction
    @Query("SELECT * FROM game WHERE id = :id")
    fun selectGame(id: Int): Flow<GameFullEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGameList(games: List<GameEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGame(game: GameEntity): Long

    @Query("DELETE FROM game")
    suspend fun deleteGames()

    @Query("DELETE FROM game WHERE id = :id")
    suspend fun deleteGames(id: Int)

    // GamesCrossRef

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGameGenreCrossRef(gameGenreCrf: GameGenreCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGameGenreListCrossRef(gameGenreCrfList: List<GameGenreCrossRef>)

    @Query("DELETE FROM game_genre_crf WHERE gId = :gameId")
    suspend fun deleteGameGenreCrossRef(gameId: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGameTagsCrossRef(gameTagsCrf: GameTagsCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGameTagsListCrossRef(gameTagsCrfList: List<GameTagsCrossRef>)

    @Query("DELETE FROM game_tags_crf WHERE gId = :gameId")
    suspend fun deleteGameTagsCrossRef(gameId: Long)
}