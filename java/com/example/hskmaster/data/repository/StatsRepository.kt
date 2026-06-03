package com.example.hskmaster.data.repository

import com.example.hskmaster.data.local.dao.UserStatsDao
import com.example.hskmaster.data.local.entity.UserStatsEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StatsRepository @Inject constructor(
    private val userStatsDao: UserStatsDao
) {

    fun getUserStats(): Flow<UserStatsEntity?> = userStatsDao.getUserStats()

    suspend fun getStats(): UserStatsEntity? = userStatsDao.getStats()

    suspend fun updateStats(stats: UserStatsEntity) {
        userStatsDao.insert(stats)
    }

    suspend fun addXP(amount: Int) {
        userStatsDao.addXP(amount)
    }

    suspend fun addLessons(amount: Int) {
        userStatsDao.addLessons(amount)
    }

    suspend fun addQuizzes(amount: Int) {
        userStatsDao.addQuizzes(amount)
    }

    suspend fun updateStreak() {
        val today = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(java.util.Date())
        userStatsDao.updateStreak(today)
    }

    suspend fun addStudyTime(minutes: Int) {
        userStatsDao.addStudyTime(minutes)
    }

    suspend fun resetStats() {
        userStatsDao.resetStats()
    }

    // Tạo stats mặc định nếu chưa có
    suspend fun initializeStatsIfNeeded() {
        val existing = userStatsDao.getStats()
        if (existing == null) {
            val today = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(java.util.Date())
            userStatsDao.insert(UserStatsEntity(
                id = 1,
                level = 1,
                xp = 0,
                streak = 0,
                studyTime = 0,
                lessonsStudied = 0,
                quizzesDone = 0,
                totalDays = 0,
                lastStudyDate = today
            ))
        }
    }
}