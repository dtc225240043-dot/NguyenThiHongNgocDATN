package com.example.hskmaster.data.repository

import com.example.hskmaster.data.local.dao.VocabDao
import com.example.hskmaster.data.local.entity.VocabEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class VocabRepository @Inject constructor(
    private val vocabDao: VocabDao
) {

    fun getAllVocabulary(): Flow<List<VocabEntity>> = flow {
        emit(vocabDao.getAllVocabulary())
    }

    fun searchVocabulary(query: String): Flow<List<VocabEntity>> = flow {
        emit(vocabDao.searchVocabulary(query))
    }

    fun getVocabularyByCategory(category: String): Flow<List<VocabEntity>> = flow {
        emit(vocabDao.getVocabularyByCategory(category))
    }

    suspend fun insertAll(vocabularies: List<VocabEntity>) {
        vocabDao.insertAll(vocabularies)
    }
}
