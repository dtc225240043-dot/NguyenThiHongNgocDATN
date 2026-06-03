package com.example.hskmaster.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.hskmaster.data.local.entity.VocabEntity

@Dao
interface VocabDao {

    // INSERT ALL
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(
        list: List<VocabEntity>
    )

    // GET ALL
    @Query("""
        SELECT * FROM vocab
    """)
    suspend fun getAllVocabulary(): List<VocabEntity>

    // GET BY CATEGORY
    @Query("""
        SELECT * FROM vocab
        WHERE category = :category
    """)
    suspend fun getVocabularyByCategory(
        category: String
    ): List<VocabEntity>

    // SEARCH
    @Query("""
        SELECT * FROM vocab
        WHERE hanzi LIKE '%' || :query || '%'
        OR pinyin LIKE '%' || :query || '%'
        OR meaning LIKE '%' || :query || '%'
        OR word LIKE '%' || :query || '%'
    """)
    suspend fun searchVocabulary(
        query: String
    ): List<VocabEntity>
}