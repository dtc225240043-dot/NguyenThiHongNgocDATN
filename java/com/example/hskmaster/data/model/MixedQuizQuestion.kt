package com.example.hskmaster.data.model

enum class QuestionType {
    MULTIPLE_CHOICE,
    FILL,
    ORDER
}

data class MixedQuizQuestion(
    val type: QuestionType,
    val question: String,


    val options: List<String> = emptyList(), // cho trắc nghiệm
    val correctAnswer: String = "",

    val words: List<String> = emptyList(), // cho sắp xếp
    val correctOrder: List<String> = emptyList()
)