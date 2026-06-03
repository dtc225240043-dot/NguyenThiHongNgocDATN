package com.example.hskmaster.data.repository

import com.example.hskmaster.data.local.entity.VocabEntity
import com.example.hskmaster.data.model.QuizQuestion

fun generateQuiz(vocabList: List<VocabEntity>): List<QuizQuestion> {

    return vocabList.map { vocab ->

        val wrongAnswers = vocabList
            .filter { it.id != vocab.id }
            .map { it.meaning }
            .shuffled()
            .take(3)

        val finalWrongAnswers = if (wrongAnswers.size < 3) {
            val extra = vocabList.map { it.meaning }.shuffled()
            (wrongAnswers + extra).distinct().take(3)
        } else {
            wrongAnswers
        }

        val options = (finalWrongAnswers + vocab.meaning)
            .distinct()
            .shuffled()

        QuizQuestion(
            question = vocab.hanzi ?: vocab.word ?: "",   // ✅ FIX ở đây
            options = options,
            correctAnswer = vocab.meaning
        )
    }
}