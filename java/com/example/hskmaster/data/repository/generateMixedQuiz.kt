package com.example.hskmaster.data.repository

import com.example.hskmaster.data.model.*

fun generateMixedQuiz(): List<MixedQuizQuestion> {

    return listOf(
        MixedQuizQuestion(
            type = QuestionType.MULTIPLE_CHOICE,
            question = "你好 nghĩa là gì?",
            options = listOf("Xin chào", "Tạm biệt", "Cảm ơn", "Xin lỗi"),
            correctAnswer = "Xin chào"
        ),
        MixedQuizQuestion(
            type = QuestionType.FILL,
            question = "nǐ hǎo = ?",
            correctAnswer = "你好"
        ),
        MixedQuizQuestion(
            type = QuestionType.ORDER,
            question = "Sắp xếp câu",
            words = listOf("学生", "我", "是"),
            correctOrder = listOf("我", "是", "学生")
        )
    )
}