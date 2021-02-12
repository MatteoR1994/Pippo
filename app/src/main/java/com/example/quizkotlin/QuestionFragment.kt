package com.example.quizkotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.children
import androidx.navigation.findNavController

class QuestionFragment : Fragment() {
    lateinit var questionText: TextView
    lateinit var answersGroup: RadioGroup
    var punteggio = 0
    var currentIndex = 0
    lateinit var sendAnswerButton: Button
    //val questionario: Questionario = QuestionarioMemoria()
    lateinit var questionario: Questionario
    //var questionario: Questionario = QuestionarioDatabase(requireContext())
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_question, container, false)

        questionario = QuestionarioDatabase(requireContext())
        questionText = v.findViewById(R.id.questionText)
        answersGroup = v.findViewById(R.id.answers)
        sendAnswerButton = v.findViewById(R.id.confirmBtn)
        makeQuestion(currentIndex)
        sendAnswerButton.setOnClickListener {  //bisogna fare una funzione che faccia questo
            val selected = answersGroup.checkedRadioButtonId
            val selectedButton = answersGroup.findViewById<RadioButton>(selected)
            var question = questionario.questionsForQuiz!![currentIndex]
            val rightAnswer = question.options[question.answer]
            //val pos = answersGroup.indexOfChild(selectedButton)
            //if (pos == currentQuestion.answer)
            if (selectedButton.text == rightAnswer) {
                punteggio++
                //questionario.riepilogo[currentIndex] = "ESATTO"
            } /*else {
                questionario.riepilogo[currentIndex] = "SBAGLIATO"
            }*/
            currentIndex++
            if(currentIndex >= questionario.questionsForQuiz!!.size) {
                checkGame(v)
            } else {
                makeQuestion(currentIndex)
            }
        }
        return v
    }
    fun makeQuestion(index: Int) {
        var currentQuestion = questionario.questionsForQuiz!![index]
        questionText.text = currentQuestion.question
        answersGroup.children.forEachIndexed { //vado a prendere i figli radioGroup e su ognuno faccio una azione e so l'indice
            index, button ->
            button as RadioButton
            button.text = currentQuestion.options[index]
        }
    }
    fun checkGame(vista: View) {
        if(punteggio >= ((questionario.questionsForQuiz!!.size * 2) / 3)) {
            vista.findNavController().navigate(R.id.action_questionFragment_to_winnerFragment)
        } else {
            vista.findNavController().navigate(R.id.action_questionFragment_to_loserFragment)
        }
    }
}