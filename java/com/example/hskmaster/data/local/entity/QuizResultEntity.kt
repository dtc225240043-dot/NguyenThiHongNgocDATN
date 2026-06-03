package com.example.hskmaster.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quiz_results")
data class QuizResultEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val topicId: String,
    val score: Int,
    val total: Int,
    val timestamp: Long = System.currentTimeMillis()
)