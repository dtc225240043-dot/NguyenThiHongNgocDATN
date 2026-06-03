package com.example.hskmaster.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val username: String,
    val password: String,
    val goal: String, // Mục tiêu học
    val currentLevel: String, // Trình độ hiện tại
    val studyTime: String, // Thời gian học mỗi ngày
    val ageGroup: String, // Độ tuổi
    val preferredLearningMethod: String // Cách học yêu thích
)