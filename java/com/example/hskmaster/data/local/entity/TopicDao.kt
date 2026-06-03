package com.example.hskmaster.data.local.dao

import androidx.room.Dao
import androidx.room.*
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.hskmaster.data.local.entity.TopicEntity


@Dao
interface TopicDao {

    // 👉 Lấy topic theo level
    @Query("SELECT * FROM topics WHERE level = :level")
    suspend fun getTopicsByLevel(level: Int): List<TopicEntity>

    // 👉 Insert dữ liệu
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(topics: List<TopicEntity>)
}