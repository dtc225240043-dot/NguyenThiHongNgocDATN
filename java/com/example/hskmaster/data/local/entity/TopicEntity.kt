package com.example.hskmaster.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "topics")
data class TopicEntity(

    @PrimaryKey
    val id: String,

    val title: String,

    val videoUrl: String,

    val level: Int
)