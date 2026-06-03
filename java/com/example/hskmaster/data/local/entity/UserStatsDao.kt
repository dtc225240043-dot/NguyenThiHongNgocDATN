package com.example.hskmaster.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.hskmaster.data.local.entity.UserStatsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserStatsDao {

    // 🔍 Lấy stats hiện tại (chỉ có 1 dòng)
    @Query("SELECT * FROM user_stats LIMIT 1")
    fun getUserStats(): Flow<UserStatsEntity?>

    @Query("SELECT * FROM user_stats LIMIT 1")
    suspend fun getStats(): UserStatsEntity?

    // 💾 Insert hoặc update (REPLACE nếu đã tồn tại)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(stats: UserStatsEntity)

    // 🔄 Update riêng (optional)
    @Update
    suspend fun update(stats: UserStatsEntity)

    // ➕ Thêm XP
    @Query("UPDATE user_stats SET xp = xp + :amount WHERE id = 1")
    suspend fun addXP(amount: Int)

     // 🔥 Cập nhật streak
     // Only update streak/totalDays when lastStudyDate is different from today
     @Query("UPDATE user_stats SET streak = streak + 1, totalDays = totalDays + 1, lastStudyDate = :date WHERE id = 1 AND lastStudyDate <> :date")
     suspend fun updateStreak(date: String)

    // ⏰ Thêm thời gian học
    @Query("UPDATE user_stats SET studyTime = studyTime + :minutes WHERE id = 1")
    suspend fun addStudyTime(minutes: Int)

    // ➕ Thêm số bài đã học
    @Query("UPDATE user_stats SET lessonsStudied = lessonsStudied + :amount WHERE id = 1")
    suspend fun addLessons(amount: Int)

    // ➕ Thêm số quiz đã làm
    @Query("UPDATE user_stats SET quizzesDone = quizzesDone + :amount WHERE id = 1")
    suspend fun addQuizzes(amount: Int)

    // ❌ Xóa toàn bộ stats (reset)
    @Query("DELETE FROM user_stats")
    suspend fun deleteAll()

    // 🔄 Reset stats về mặc định
    @Query("UPDATE user_stats SET xp = 0, streak = 0, level = 1, studyTime = 0, lessonsStudied = 0, quizzesDone = 0, totalDays = 0 WHERE id = 1")
    suspend fun resetStats()
}