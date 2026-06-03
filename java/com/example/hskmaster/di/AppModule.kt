package com.example.hskmaster.di

import android.content.Context
import com.example.hskmaster.data.local.AppDatabase
import com.example.hskmaster.data.local.dao.*
import com.example.hskmaster.data.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    @Singleton
    fun provideUserStatsDao(database: AppDatabase): UserStatsDao {
        return database.userStatsDao()
    }

    @Provides
    @Singleton
    fun provideVocabDao(database: AppDatabase): VocabDao {
        return database.vocabDao()
    }

    @Provides
    @Singleton
    fun provideGrammarDao(database: AppDatabase): GrammarDao {
        return database.grammarDao()
    }

    @Provides
    @Singleton
    fun provideTopicDao(database: AppDatabase): TopicDao {
        return database.topicDao()
    }

    @Provides
    @Singleton
    fun provideQuizResultDao(database: AppDatabase): QuizResultDao {
        return database.quizResultDao()
    }
}

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(): UserRepository {
        return UserRepository()
    }

    @Provides
    @Singleton
    fun provideStatsRepository(userStatsDao: UserStatsDao): StatsRepository {
        return StatsRepository(userStatsDao)
    }

    @Provides
    @Singleton
    fun provideVocabRepository(vocabDao: VocabDao): VocabRepository {
        return VocabRepository(vocabDao)
    }

    @Provides
    @Singleton
    fun provideGrammarRepository(grammarDao: GrammarDao): GrammarRepository {
        return GrammarRepository(grammarDao)
    }

    @Provides
    @Singleton
    fun provideTopicRepository(topicDao: TopicDao): TopicRepository {
        return TopicRepository(topicDao)
    }
}
