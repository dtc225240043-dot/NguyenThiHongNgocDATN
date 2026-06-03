package com.example.hskmaster.data.model

data class UserProgress(
    val currentLevel: Int = 1,
    val targetLevel: Int = 4,
    val studyTimePerDay: Int = 30
)