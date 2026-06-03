package com.example.hskmaster.data.repository

import com.example.hskmaster.data.local.dao.GrammarDao
import com.example.hskmaster.data.local.entity.GrammarEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GrammarRepository @Inject constructor(
    private val grammarDao: GrammarDao
) {

    fun getGrammarByTopic(topicId: String): Flow<List<GrammarEntity>> = flow {
        emit(grammarDao.getGrammarByTopic(topicId))
    }

    suspend fun insertAll(grammarList: List<GrammarEntity>) {
        grammarDao.insertAll(grammarList)
    }
}
