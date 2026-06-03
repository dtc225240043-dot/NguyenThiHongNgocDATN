package com.example.hskmaster

import android.app.Application
import com.example.hskmaster.data.SeedData
import com.example.hskmaster.data.local.AppDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HSKMasterApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Seed database khi app start
        GlobalScope.launch {
            try {
                val db = AppDatabase.getDatabase(this@HSKMasterApplication)
                SeedData.seed(db)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
