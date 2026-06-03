package com.example.hskmaster.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "grammar")
data class GrammarEntity(

    @PrimaryKey
    val id: String,

    val topicId: String, // 🔥 liên kết Topic

    val content: String
)