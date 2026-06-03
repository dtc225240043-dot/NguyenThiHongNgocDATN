package com.example.hskmaster.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.hskmaster.data.local.entity.QuizResultEntity

@Dao
interface QuizResultDao {

    @Insert
    suspend fun insert(result: QuizResultEntity)

    @Query("SELECT COUNT(*) FROM quiz_results")
    suspend fun getTotalAttempts(): Int

    @Query("SELECT MAX(score) FROM quiz_results")
    suspend fun getBestScoreAll(): Int?

    @Query("SELECT AVG(score) FROM quiz_results")
    suspend fun getAverageScoreAll(): Float?
}