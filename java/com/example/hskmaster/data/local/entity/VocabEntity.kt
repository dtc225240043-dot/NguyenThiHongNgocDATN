package com.example.hskmaster.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vocab")
data class VocabEntity(

    @PrimaryKey
    val id: String,
    val hanzi: String?,
    val topicId: String, // 🔥 liên kết với Topic

    val word: String?,
    val pinyin: String,
    val meaning: String,
    val level: Int,
    val category: String?


)