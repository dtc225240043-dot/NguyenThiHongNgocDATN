package com.example.hskmaster.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.hskmaster.data.local.dao.*
import com.example.hskmaster.data.local.entity.*

@Database(
        entities = [
        UserEntity::class,
        UserStatsEntity::class,
        GrammarEntity::class,
        VocabEntity::class,
        TopicEntity::class,
        QuizResultEntity::class,
        com.example.hskmaster.data.local.entity.FavoriteEntity::class,
        com.example.hskmaster.data.local.entity.CompletedLesson::class
    ],
    // bumped version to include FavoriteEntity and CompletedLesson tables
    version = 97
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun grammarDao(): GrammarDao
    abstract fun userDao(): UserDao
    abstract fun userStatsDao(): UserStatsDao
    abstract fun vocabDao(): VocabDao
    abstract fun favoriteDao(): com.example.hskmaster.data.local.dao.FavoriteDao
    abstract fun topicDao(): TopicDao
    abstract fun quizResultDao(): QuizResultDao
    abstract fun completedLessonDao(): com.example.hskmaster.data.local.dao.CompletedLessonDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "hsk_db"
                )
                        // Register explicit migrations. Keep fallbackToDestructiveMigration as a last resort.
                            .addMigrations(com.example.hskmaster.data.local.MIGRATION_87_88)
                            .fallbackToDestructiveMigration()
                            .build()
                INSTANCE = instance
                instance
            }
        }
    }
}