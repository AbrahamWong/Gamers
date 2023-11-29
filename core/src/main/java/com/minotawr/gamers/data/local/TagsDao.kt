package com.minotawr.gamers.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.minotawr.gamers.data.local.entity.TagsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TagsDao {

    // Rating
    @Query("SELECT * FROM tag")
    fun selectAllTags(): Flow<List<TagsEntity>>

    @Query("SELECT * FROM tag WHERE tagId = :id")
    fun selectTags(id: Int): Flow<TagsEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTagsList(tags: List<TagsEntity>): LongArray

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTags(tags: TagsEntity): Long

    @Query("DELETE FROM tag")
    suspend fun deleteTags()

}