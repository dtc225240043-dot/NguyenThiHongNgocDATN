package com.example.hskmaster.data.model

data class QuizQuestion(
    val question: String,
    val options: List<String>,
    val correctAnswer: String
)