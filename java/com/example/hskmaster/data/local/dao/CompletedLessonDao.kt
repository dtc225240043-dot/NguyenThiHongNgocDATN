package com.example.hskmaster.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.hskmaster.data.local.entity.CompletedLesson

@Dao
interface CompletedLessonDao {
    @Query("SELECT COUNT(*) FROM completed_lessons WHERE lessonId = :id")
    suspend fun countById(id: String): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(lesson: CompletedLesson)
}

