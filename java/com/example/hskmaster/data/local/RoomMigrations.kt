package com.example.hskmaster.data.local

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

// Migration from version 87 -> 88: add completed_lessons table
val MIGRATION_87_88 = object : Migration(87, 88) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `completed_lessons` (
                `lessonId` TEXT NOT NULL,
                `completedAt` TEXT NOT NULL,
                PRIMARY KEY(`lessonId`)
            )
            """.trimIndent()
        )
    }
}

