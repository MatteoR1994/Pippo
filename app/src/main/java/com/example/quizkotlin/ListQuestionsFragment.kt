package com.example.quizkotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListQuestionsFragment : Fragment() {
    lateinit var linearLayout: LinearLayoutManager
    lateinit var questionsL: RecyclerView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_list_questions, container, false)
        val questionsList = QuestionarioDatabase(requireContext()).questions
        linearLayout = LinearLayoutManager(requireContext())
        questionsL = v.findViewById(R.id.questionsList)
        questionsL.layoutManager = linearLayout
        val adapter = QuestionAdapter(questionsList!!)
        questionsL.adapter = adapter
        return v
    }
}