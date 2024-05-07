package com.example.sharequestion.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.sharequestion.databinding.CommentRowBinding
import com.example.sharequestion.databinding.QuestionRowBinding
import com.example.sharequestion.model.Comment
import com.example.sharequestion.util.downloadUrl

class CommentRowAdapter(val commentList:ArrayList<Comment>):
    Adapter<CommentRowAdapter.CommmentViewHolder>() {

    class CommmentViewHolder(val binding: CommentRowBinding): ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommmentViewHolder {
        val binding = CommentRowBinding.inflate(LayoutInflater.from(parent.context))
        return CommmentViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return commentList.size
    }

    override fun onBindViewHolder(holder: CommmentViewHolder, position: Int) {
        if (commentList[position].commentUri == ""){
            holder.binding.answerImage.visibility = View.GONE
            holder.binding.answerText.text = commentList[position].commentText
        }
        else{
            holder.binding.answerImage.downloadUrl(commentList[position].commentUri.toString())
            holder.binding.answerText.text = commentList[position].commentText
        }

    }
}