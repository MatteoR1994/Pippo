package com.example.quizkotlin

import android.content.ContentValues
import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.quizkotlin.DatabaseManager.QuestionEntry.COLUMN_OPZIONE1
import com.example.quizkotlin.DatabaseManager.QuestionEntry.COLUMN_OPZIONE2
import com.example.quizkotlin.DatabaseManager.QuestionEntry.COLUMN_OPZIONE3
import com.example.quizkotlin.DatabaseManager.QuestionEntry.COLUMN_RISPOSTA
import com.example.quizkotlin.DatabaseManager.QuestionEntry.COLUMN_TESTO
import com.example.quizkotlin.DatabaseManager.QuestionEntry.TABLE_NAME
import kotlinx.android.synthetic.main.fragment_edit_question.*

// Questo è il fragment che permette di modificare una domanda.
class EditQuestionFragment : Fragment() {
    // Imposto, come sempre, le variabili di cui avremo bisogno.
    lateinit var editedQuestionText: EditText
    lateinit var editedOptionAnswer1: EditText
    lateinit var editedOptionAnswer2: EditText
    lateinit var editedOptionAnswer3: EditText
    lateinit var editedRightAnswer: EditText
    lateinit var updateQuestionButton: Button
    var txtQ: String? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v= inflater.inflate(R.layout.fragment_edit_question, container, false)
        editedQuestionText = v.findViewById(R.id.editedQuestionText)
        editedOptionAnswer1 = v.findViewById(R.id.editedOptionAnswer1)
        editedOptionAnswer2 = v.findViewById(R.id.editedOptionAnswer2)
        editedOptionAnswer3 = v.findViewById(R.id.editedOptionAnswer3)
        editedRightAnswer = v.findViewById(R.id.editedRightAnswer)
        updateQuestionButton = v.findViewById(R.id.editQuestionButton)
        txtQ = arguments?.getString("question") // Recupero il parametro passato al fragment
        // Seleziono tutti i dati necessari alla domanda (testo, 3 opzioni di risposta e risposta esatta).
        val mDBHelper = QuestionDBHelper(requireContext())
        val db = mDBHelper.readableDatabase
        val projection = arrayOf(COLUMN_TESTO, COLUMN_OPZIONE1, COLUMN_OPZIONE2, COLUMN_OPZIONE3, COLUMN_RISPOSTA)
        val selection = "$COLUMN_TESTO = ?"
        val selectionArgs = arrayOf("$txtQ")
        val cursor : Cursor = db.query(TABLE_NAME, projection, selection, selectionArgs, null, null , null)
        val questionTextColumnIndex = cursor.getColumnIndexOrThrow(COLUMN_TESTO)
        val option1ColumnIndex = cursor.getColumnIndexOrThrow(COLUMN_OPZIONE1)
        val option2ColumnIndex = cursor.getColumnIndexOrThrow(COLUMN_OPZIONE2)
        val option3ColumnIndex = cursor.getColumnIndexOrThrow(COLUMN_OPZIONE3)
        val rightAnswerColumnIndex = cursor.getColumnIndexOrThrow(COLUMN_RISPOSTA)
        while(cursor.moveToNext()) {
            val newQuestionString = cursor.getString(questionTextColumnIndex)
            val newOption1String = cursor.getString(option1ColumnIndex)
            val newOption2String = cursor.getString(option2ColumnIndex)
            val newOption3String = cursor.getString(option3ColumnIndex)
            val newRightAnswer = cursor.getInt(rightAnswerColumnIndex)
            // Imposto il testo dei campi input EditText (presenti in fragment_edit_question.xml) con i valori corrispondenti
            // selezionati dal database in base alla domanda passata, per poterli modificare.
            editedQuestionText.setText(newQuestionString)
            editedOptionAnswer1.setText(newOption1String)
            editedOptionAnswer2.setText(newOption2String)
            editedOptionAnswer3.setText(newOption3String)
            editedRightAnswer.setText(newRightAnswer.toString())
        }
        cursor.close()
        // Alla pressione del bottone per modificare...
        updateQuestionButton.setOnClickListener {
            val db = mDBHelper.writableDatabase
            // Imposto, come sempre, i valori da inserire con i nuovi input prelevati dal form nel layout.
            val values = ContentValues().apply {
                put(COLUMN_TESTO, editedQuestionText.text.toString())
                put(COLUMN_OPZIONE1, editedOptionAnswer1.text.toString())
                put(COLUMN_OPZIONE2, editedOptionAnswer2.text.toString())
                put(COLUMN_OPZIONE3, editedOptionAnswer3.text.toString())
                put(COLUMN_RISPOSTA, editedRightAnswer.text.toString().toInt())
            }
            val selectionArgs2 = arrayOf("$txtQ")
            // Eseguo la query di update nel database:
            // Aggiorna i valori "values" nella tabella "TABLE_NAME" dove il testo della domanda è uguale a "selectionArgs2"
            val rowId = db.update(TABLE_NAME, values, "$COLUMN_TESTO = ?", selectionArgs2)
            // A query eseguita, torno al fragment della lista delle domande. Se tutto va liscio, quando lo riapirà la lista si aggiornerà da sola.
            v.findNavController().navigate(R.id.action_editQuestionFragment_to_listQuestionsFragment)
        }
        return v
    }
}