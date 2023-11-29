package com.minotawr.gamers.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation

@Entity(primaryKeys = ["gId", "gnId"], tableName = "game_genre_crf")
data class GameGenreCrossRef(
    val gId: Long,

    @ColumnInfo(index = true)
    val gnId: Long,
)

@Entity(primaryKeys = ["gId", "tgId"], tableName = "game_tags_crf")
data class GameTagsCrossRef(
    val gId: Long,

    @ColumnInfo(index = true)
    val tgId: Long,
)

data class GameFullEntity(
    @Embedded
    val game: GameEntity,

    @Relation(
        parentColumn = "gameId",
        entity = GenreEntity::class,
        entityColumn = "genreId",
        associateBy = Junction(
            value = GameGenreCrossRef::class,
            parentColumn = "gId",
            entityColumn = "gnId"
        )
    )
    val genres: List<GenreEntity>,

    @Relation(
        parentColumn = "gameId",
        entity = TagsEntity::class,
        entityColumn = "tagId",
        associateBy = Junction(
            value = GameTagsCrossRef::class,
            parentColumn = "gId",
            entityColumn = "tgId"
        )
    )
    val tags: List<TagsEntity>
)