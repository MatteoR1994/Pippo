package com.example.quizkotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class EditQuestionFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
         val txtQ = arguments?.getString("question")
        // Inflate the layout for this fragment
      val v= inflater.inflate(R.layout.fragment_edit_question, container, false)
        return v
    }



}