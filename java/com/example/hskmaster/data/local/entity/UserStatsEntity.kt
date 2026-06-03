package com.example.hskmaster.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_stats")
data class UserStatsEntity(

    @PrimaryKey
    val id: Int = 1, // chỉ 1 dòng duy nhất

    val xp: Int = 0,
    val level: Int = 1,
    val streak: Int = 0,
    val studyTime: Int = 0, // thời gian học tính bằng phút
    val lessonsStudied: Int = 0, // số bài/vocab đã học (thực tế)
    val quizzesDone: Int = 0, // số quiz đã làm
    val totalDays: Int = 0, // tổng số ngày đã học (không phải continuous)
    val lastStudyDate: String = ""
)