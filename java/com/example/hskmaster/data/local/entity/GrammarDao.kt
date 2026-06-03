package com.example.hskmaster.data.local.dao

import androidx.room.*
import com.example.hskmaster.data.local.entity.GrammarEntity

@Dao
interface GrammarDao {

    @Query("SELECT * FROM grammar WHERE topicId = :topicId")
    suspend fun getGrammarByTopic(topicId: String): List<GrammarEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<GrammarEntity>)
}