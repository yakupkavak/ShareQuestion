package com.example.sharequestion.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.sharequestion.databinding.QuestionRowBinding
import com.example.sharequestion.model.Question
import com.example.sharequestion.util.downloadUrl

class QuestionRowAdapter(val questionList: ArrayList<Question>): RecyclerView.Adapter<QuestionRowAdapter.QuestionViewHolder>() {
    class QuestionViewHolder(val binding: QuestionRowBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val binding = QuestionRowBinding.inflate(LayoutInflater.from(parent.context))
        return QuestionViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return questionList.size
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        holder.binding.questionImageRow.downloadUrl(questionList[position].questionUri)
        println("adapter worked")
        holder.binding.questionTextRow.text = questionList[position].questionText
        holder.binding.questionSaveImg.setOnClickListener {
            println("clicked")
        }

    }
}