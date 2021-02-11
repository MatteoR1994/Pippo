package com.example.quizkotlin

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class QuestionAdapter(private var questionList: List<Question>) : RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>() {
    class QuestionViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private lateinit var question: Question
        private var view: View
        private var questionText: TextView
        private var optionAnswer1: TextView
        private var optionAnswer2: TextView
        private var optionAnswer3: TextView
        init {
            view = v
            questionText = view.findViewById(R.id.questionTextItem)

            optionAnswer2 = view.findViewById(R.id.optionAnswer2Item)
            optionAnswer3 = view.findViewById(R.id.optionAnswer3Item)
            optionAnswer1 = view.findViewById(R.id.optionAnswer1Item)
        }
        fun bindQuestion(question: Question) {
            this.question = question
            this.questionText.text = question.question
            this.optionAnswer1.text = question.options[0]
            this.optionAnswer2.text = question.options[1]
            this.optionAnswer3.text = question.options[2]
            when(question.answer) {
                0 -> {
                    this.optionAnswer1.setTextColor(Color.GREEN)
                }
                1 -> {
                    this.optionAnswer2.setTextColor(Color.GREEN)
                }
                2 -> {
                    this.optionAnswer3.setTextColor(Color.GREEN)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.question_details_layout,parent,false)
        return QuestionViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val item = questionList[position]
        holder.bindQuestion(item)
    }

    override fun getItemCount(): Int {
        return questionList.size
    }
}