package com.example.quizkotlin

import android.content.Context
import android.database.Cursor
import com.example.quizkotlin.DatabaseManager.QuestionEntry.COLUMN_OPZIONE1
import com.example.quizkotlin.DatabaseManager.QuestionEntry.COLUMN_OPZIONE2
import com.example.quizkotlin.DatabaseManager.QuestionEntry.COLUMN_OPZIONE3
import com.example.quizkotlin.DatabaseManager.QuestionEntry.COLUMN_RISPOSTA
import com.example.quizkotlin.DatabaseManager.QuestionEntry.COLUMN_RISPOSTAU
import com.example.quizkotlin.DatabaseManager.QuestionEntry.COLUMN_TESTO
import com.example.quizkotlin.DatabaseManager.QuestionEntry.TABLE_NAME
import com.example.quizkotlin.DatabaseManager.QuestionEntry._ID

class QuestionarioDatabase(val context: Context) : Questionario {
    //var _questions: MutableList<Question>? = null
    var _questions: MutableList<Question>? = null
    override val questions: MutableList<Question>?
        get() {
            if(_questions == null) {
                //readQuestions()
                _questions = readAllQuestions()
            }
            return _questions
        }
    override var questionsForQuiz: List<Question>? = null

    get() {
        if(_questions == null) {
            //readQuestions()
            _questions = readAllQuestions()
            _questions!!.shuffle()
        }
        // Qua ritorna alla fine 5 domande, casuali.
        return _questions!!.take(5)
    }
    // override lateinit var riepilogo: MutableList<String>

    fun readAllQuestions(): MutableList<Question> {
        val db = QuestionDBHelper(context).readableDatabase
        val projection = arrayOf(_ID, COLUMN_TESTO, COLUMN_OPZIONE1, COLUMN_OPZIONE2, COLUMN_OPZIONE3, COLUMN_RISPOSTA, COLUMN_RISPOSTAU)

        val cursor : Cursor = db.query(TABLE_NAME, projection, null, null, null, null, null)

        val idColumnIndex = cursor.getColumnIndexOrThrow(_ID)
        val questionColumnIndex = cursor.getColumnIndexOrThrow(COLUMN_TESTO)
        val answerOption1ColumnIndex = cursor.getColumnIndexOrThrow(COLUMN_OPZIONE1)
        val answerOption2ColumnIndex = cursor.getColumnIndexOrThrow(COLUMN_OPZIONE2)
        val answerOption3ColumnIndex = cursor.getColumnIndexOrThrow(COLUMN_OPZIONE3)
        val answerColumnIndex = cursor.getColumnIndexOrThrow(COLUMN_RISPOSTA)
        val userAnswerColumnIndex = cursor.getColumnIndexOrThrow(COLUMN_RISPOSTAU)

        val questionsList = mutableListOf<Question>()

        while (cursor.moveToNext()) {
            val currentId = cursor.getInt(idColumnIndex)
            val currentQuestionText = cursor.getString(questionColumnIndex)
            val currentOptionAnswer1 = cursor.getString(answerOption1ColumnIndex)
            val currentOptionAnswer2 = cursor.getString(answerOption2ColumnIndex)
            val currentOptionAnswer3 = cursor.getString(answerOption3ColumnIndex)
            val currentAnswer = cursor.getInt(answerColumnIndex)
            val currentUserAnswer = cursor.getInt(userAnswerColumnIndex)

            val opzioniRisposta = mutableListOf<String>(currentOptionAnswer1,currentOptionAnswer2,currentOptionAnswer3)
            val domanda = Question(currentQuestionText,opzioniRisposta,currentAnswer,currentUserAnswer)

            questionsList.add(domanda)
        }
        cursor.close()
        return questionsList
    }
}