package com.example.quizkotlin

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.quizkotlin.DatabaseManager.QuestionEntry.COLUMN_TESTO
import com.example.quizkotlin.DatabaseManager.QuestionEntry.TABLE_NAME
import com.example.quizkotlin.DatabaseManager.QuestionEntry._ID
import kotlinx.android.synthetic.main.question_details_layout.view.*

class QuestionAdapter(private var questionList: MutableList<Question>) : RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>() {
    class QuestionViewHolder(v: View) : RecyclerView.ViewHolder(v),View.OnClickListener{ // ",View.OnClickListener" va messo per poter rendere
        // cliccabile l'elemento della lista.
        private lateinit var question: Question
        private var view: View
        private var questionText: TextView
        private var optionAnswer1: TextView
        private var optionAnswer2: TextView
        private var optionAnswer3: TextView
        init {
            view = v
            v.setOnClickListener(this) // Questo indica che la nostra v (quindi l'elemento della lista) avrà settato su se stessa un metodo
            // onClickListener, per poter implementare il metodo onClick.
            questionText = view.findViewById(R.id.questionTextItem)
            optionAnswer1 = view.findViewById(R.id.optionAnswer1Item)
            optionAnswer2 = view.findViewById(R.id.optionAnswer2Item)
            optionAnswer3 = view.findViewById(R.id.optionAnswer3Item)
        }
        fun bindQuestion(parent: QuestionAdapter  , question: Question, position: Int) {
            view.deleteBtn.setOnClickListener { // Il listener del bottone abbiamo dovuto metterlo qua (in realtà
                // dovrebbe essere dentro a "onCreateViewHolder") perché avevamo necessità della posizione dell'elemento
                // nella lista, per fare il removeAt e per il valore da passare nella query.
                val mDBHelper = QuestionDBHelper(view.context)
                val db = mDBHelper.writableDatabase
                val selection = "${COLUMN_TESTO} = ?" // Come condizione del WHERE abbiamo usato il testo della domanda (che poi
                // è passato anche per la modifica, vedi sotto) perché quando abbiamo costruito il database non abbiamo previsto la
                // presenza dell'id per ogni riga.
                val selectionArgs = arrayOf( "${parent.questionList[position].question}") // Questo permette di prendere la lista passata in input
                // all'adapter, come detto in "onBindViewHolder". Perché da dentro a questa classe interna non potevamo accedere a "questionList".
                db.delete(TABLE_NAME, selection, selectionArgs)
                parent.questionList.removeAt(position)
                parent.notifyDataSetChanged()
            }
            this.question = question
            this.questionText.text = question.question
            this.optionAnswer1.text = question.options[0]
            this.optionAnswer2.text = question.options[1]
            this.optionAnswer3.text = question.options[2]
            // In questo when abbiamo dovuto settare il colore del testo delle opzioni sbagliate a nero, e quello
            // di quella giusta verde perché quando il programma riciclava le viste degli elementi della lista
            // aumentava le risposte verdi. Da 1 che doveva essere, ne metteva 2 o anche 3, perché "riciclava" la
            // vista di quelle sopra.
            when(question.answer) {
                0 -> {
                    this.optionAnswer1.setTextColor(Color.GREEN)
                    this.optionAnswer2.setTextColor(Color.RED)
                    this.optionAnswer3.setTextColor(Color.RED)
                }
                1 -> {
                    this.optionAnswer1.setTextColor(Color.RED)
                    this.optionAnswer2.setTextColor(Color.GREEN)
                    this.optionAnswer3.setTextColor(Color.RED)
                }
                2 -> {
                    this.optionAnswer1.setTextColor(Color.RED)
                    this.optionAnswer2.setTextColor(Color.RED)
                    this.optionAnswer3.setTextColor(Color.GREEN)
                }
            }
        }
        override fun onClick(v: View?) {
            // Alla pressione dell'elemento della lista (qualsiasi punto al di fuori delle stringhe e del bottone per
            // cancellare, compreso tra le due righe rosse) si apre il fragment che permette di modificare la domanda e
            // aggiornare il database.
            // La prima riga imposta i parametri da passare al nuovo fragment:
            // x to y vuol dire che metti il valore y nella variabile x.
            // La seconda riga, come sempre, naviga tra i due fragment.
            val bundle = bundleOf("question" to this.questionText.text.toString())
            v?.findNavController()?.navigate(R.id.action_listQuestionsFragment_to_editQuestionFragment, bundle)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.question_details_layout,parent,false)
        //val holder= QuestionViewHolder(view) // Questo non considerarlo.
        return QuestionViewHolder(view)
    }
    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val item = questionList[position]
        holder.bindQuestion(this, item, position) // Qui, a differenza del progetto di Riccardo del diario, abbiamo dovuto
        // inserire due nuovi parametri "parent" e "position" per poter identificare l'elemento da cancellare (position) all'interno
        // della nostra lista (this), non avendo gli id.
    }
    override fun getItemCount(): Int {
        return questionList.size
    }
}