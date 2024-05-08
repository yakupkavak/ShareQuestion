package com.example.sharequestion.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.sharequestion.databinding.QuestionRowBinding
import com.example.sharequestion.model.Question

class SavedQuestionAdapter(val questionList:ArrayList<Question>): Adapter<SavedQuestionAdapter.SavedViewHolder>() {

    class SavedViewHolder(val binding : QuestionRowBinding): ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedViewHolder {
        val binding = QuestionRowBinding.inflate(LayoutInflater.from(parent.context))
        return SavedViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return questionList.size
    }

    override fun onBindViewHolder(holder: SavedViewHolder, position: Int) {
        println(questionList[position].questionText)
    }
}