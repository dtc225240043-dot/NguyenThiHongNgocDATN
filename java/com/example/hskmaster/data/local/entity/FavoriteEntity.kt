package com.example.hskmaster.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_vocab")
data class FavoriteEntity(
    @PrimaryKey
    val id: String,
    val hanzi: String?,
    val topicId: String,
    val word: String?,
    val pinyin: String,
    val meaning: String,
    val level: Int,
    val category: String?
)

