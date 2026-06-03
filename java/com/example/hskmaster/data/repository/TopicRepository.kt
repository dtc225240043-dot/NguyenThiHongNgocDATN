package com.example.hskmaster.data.repository

import com.example.hskmaster.data.local.dao.TopicDao
import com.example.hskmaster.data.local.entity.TopicEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TopicRepository @Inject constructor(
    private val topicDao: TopicDao
) {

    fun getTopicsByLevel(level: Int): Flow<List<TopicEntity>> = flow {
        emit(topicDao.getTopicsByLevel(level))
    }

    suspend fun insertAll(topics: List<TopicEntity>) {
        topicDao.insertAll(topics)
    }
}
