package com.example.quizkotlin

interface Questionario {
    val questionsForQuiz: List<Question>?
    val questions: MutableList<Question>?
    //val riepilogo: MutableList<String>
}