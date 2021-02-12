package com.example.quizkotlin

class QuestionarioMemoria() : Questionario {
        override val questionsForQuiz= mutableListOf(
            Question("Cos'è una classe?", listOf(
                "una questione di stile",
                "un social network",
                "un tipo di dato definito da stato e comportamento"
            ),2),
            Question("Cos'è il polimorfismo?", listOf(
                "un animale africano",
                "la capacità di un reference x di puntare ad un oggetto che implementa/estende x",
                "una pratica carnevalesca"
            ),1),
            Question("Perchè studiamo kotlin", listOf(
                "perchè java è superato per le app android",
                "mi piace come suona il nome",
                "per masochismo"
            ),0),
        )
    override val questions: MutableList<Question>?
        get() = questionsForQuiz
    //val riepilogo = Array<String>(3){""}
        //override val riepilogo = mutableListOf("","","")
}