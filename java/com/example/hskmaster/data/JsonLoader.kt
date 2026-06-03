
package com.example.hskmaster.data

import android.content.Context
import com.example.hskmaster.data.model.LessonModel
import com.example.hskmaster.data.model.Vocabulary
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object JsonLoader {

    // =========================
    // LOAD HSK JSON
    // =========================
    fun loadLessons(
        context: Context,
        fileName: String
    ): List<LessonModel> {

        return try {

            val json = context.assets
                .open(fileName)
                .bufferedReader()
                .use {
                    it.readText()
                }

            val type =
                object : TypeToken<List<LessonModel>>() {}.type

            Gson().fromJson(json, type)

        } catch (e: Exception) {

            emptyList()
        }
    }

    // =========================
    // LOAD VOCAB CATEGORY JSON
    // =========================
    fun loadVocabulary(
        context: Context,
        fileName: String
    ): List<Vocabulary> {

        return try {

            val json = context.assets
                .open(fileName)
                .bufferedReader()
                .use {
                    it.readText()
                }

            val type =
                object : TypeToken<List<Vocabulary>>() {}.type

            Gson().fromJson(json, type)

        } catch (e: Exception) {

            emptyList()
        }
    }
}

