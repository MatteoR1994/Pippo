package com.example.quizkotlin

import android.content.ContentValues
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.findNavController
import com.example.quizkotlin.DatabaseManager.QuestionEntry.TABLE_NAME

class NewQuestionFragment : Fragment() {
    lateinit var questionText: EditText
    lateinit var optionAnswer1: EditText
    lateinit var optionAnswer2: EditText
    lateinit var optionAnswer3: EditText
    lateinit var rightAnswer: EditText
    lateinit var saveButton: Button
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_new_question, container, false)
        questionText = v.findViewById(R.id.questionTextInput)
        optionAnswer1 = v.findViewById(R.id.optionAnswer1)
        optionAnswer2 = v.findViewById(R.id.optionAnswer2)
        optionAnswer3 = v.findViewById(R.id.optionAnswer3)
        rightAnswer = v.findViewById(R.id.rightAnswer)
        saveButton = v.findViewById(R.id.saveQuestionButton)
        saveButton.setOnClickListener {
            saveQuestion()
            v.findNavController().navigate(R.id.action_newQuestionFragment_to_startFragment)
        }
        return v
    }
    fun saveQuestion() {
        val text = questionText.text.toString()
        val opzione1 = optionAnswer1.text.toString()
        val opzione2 = optionAnswer2.text.toString()
        val opzione3 = optionAnswer3.text.toString()
        val esatta = rightAnswer.text.toString()
        val mDBHelper = QuestionDBHelper(requireContext())
        val db = mDBHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseManager.QuestionEntry.COLUMN_TESTO, text)
            put(DatabaseManager.QuestionEntry.COLUMN_OPZIONE1, opzione1)
            put(DatabaseManager.QuestionEntry.COLUMN_OPZIONE2, opzione2)
            put(DatabaseManager.QuestionEntry.COLUMN_OPZIONE3, opzione3)
            put(DatabaseManager.QuestionEntry.COLUMN_RISPOSTA, esatta)
            put(DatabaseManager.QuestionEntry.COLUMN_RISPOSTAU, -1)
        }
        val rowId = db.insert(TABLE_NAME, null, values)
    }
}