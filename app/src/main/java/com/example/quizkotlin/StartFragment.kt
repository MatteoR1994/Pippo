package com.example.quizkotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_start.*
// serve per non fare findViewByID



class StartFragment : Fragment() {

    lateinit var buttonStart: Button
    lateinit var buttonNewQuestion: Button
    lateinit var buttonShowQuestions: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_start, container, false)
        buttonStart = v.findViewById(R.id.startButton)
        buttonNewQuestion = v.findViewById(R.id.newQuestionBtn)
        buttonShowQuestions = v.findViewById(R.id.showQuestionsButton)
        buttonStart.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_startFragment_to_questionFragment)
        )
        buttonNewQuestion.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_startFragment_to_newQuestionFragment)
        )
        buttonShowQuestions.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_startFragment_to_listQuestionsFragment)
        )
        return v

    }


}