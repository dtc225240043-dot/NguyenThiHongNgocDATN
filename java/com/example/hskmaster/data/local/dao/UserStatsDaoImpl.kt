package com.example.hskmaster.data.local.dao

import com.example.hskmaster.data.local.dao.UserStatsDao
import com.example.hskmaster.data.local.entity.UserStatsEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserStatsDaoImpl @Inject constructor(
    private val dao: UserStatsDao
) : UserStatsDao {

    override fun getUserStats(): Flow<UserStatsEntity?> = dao.getUserStats()

    override suspend fun getStats(): UserStatsEntity? = dao.getStats()

    override suspend fun insert(stats: UserStatsEntity) = dao.insert(stats)

    override suspend fun update(stats: UserStatsEntity) = dao.update(stats)

    override suspend fun addXP(amount: Int) = dao.addXP(amount)

    override suspend fun updateStreak(date: String) = dao.updateStreak(date)

    override suspend fun addStudyTime(minutes: Int) = dao.addStudyTime(minutes)

    override suspend fun addLessons(amount: Int) = dao.addLessons(amount)

    override suspend fun addQuizzes(amount: Int) = dao.addQuizzes(amount)

    override suspend fun deleteAll() = dao.deleteAll()

    override suspend fun resetStats() = dao.resetStats()
}
