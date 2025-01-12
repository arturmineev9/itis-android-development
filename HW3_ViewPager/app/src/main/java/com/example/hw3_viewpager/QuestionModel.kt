package com.example.hw3_viewpager

// Модель вопроса
data class QuestionModel(
    val id: Int,
    val question : String,
    val answers: List<String>
)