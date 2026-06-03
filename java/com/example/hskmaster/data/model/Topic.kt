package com.example.hskmaster.data.model

data class Topic(

    val id: String,

    val title: String,

    val videoUrl: String,

    val vocabulary: List<Vocabulary>,

    val grammar: List<Grammar>,

    val quiz: List<Quiz>
)