package com.example.hskmaster.data.local

import android.content.Context
import androidx.room.Room

object DatabaseProvider {

    fun getDatabase(context: Context): AppDatabase {
        // Use AppDatabase singleton provider to avoid creating multiple instances
        return AppDatabase.getDatabase(context.applicationContext)
    }
}