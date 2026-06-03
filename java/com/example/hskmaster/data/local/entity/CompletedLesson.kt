package com.example.hskmaster.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "completed_lessons")
data class CompletedLesson(
    @PrimaryKey
    val lessonId: String,
    val completedAt: String // store date string like yyyy-MM-dd
)

