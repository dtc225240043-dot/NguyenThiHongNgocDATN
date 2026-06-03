package com.example.hskmaster.data.model

data class LessonModel(

    val id: String,

    val title: String,

    val videoUrl: String,

    val vocabularies: List<Vocabulary>,

    val grammars: List<Grammar>,

    val quizzes: List<Quiz>
)