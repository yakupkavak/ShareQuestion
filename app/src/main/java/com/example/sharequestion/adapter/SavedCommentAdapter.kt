package com.example.sharequestion.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.sharequestion.databinding.CommentRowBinding
import com.example.sharequestion.model.Comment
import com.example.sharequestion.util.convertToBitmap
import com.example.sharequestion.util.downloadUrl

class SavedCommentAdapter(val commentList : ArrayList<Comment>):Adapter<SavedCommentAdapter.SavedCommentHolder>() {

    class SavedCommentHolder(val binding: CommentRowBinding):ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedCommentHolder {
        val binding = CommentRowBinding.inflate(LayoutInflater.from(parent.context))
        return SavedCommentHolder(binding)
    }

    override fun getItemCount(): Int {
        return commentList.size
    }

    override fun onBindViewHolder(holder: SavedCommentHolder, position: Int) {
        if (commentList[position].commentUri == ""){
            holder.binding.answerImage.visibility = View.GONE
            holder.binding.answerText.text = commentList[position].commentText
        }
        else{
            /* There is an problem
            holder.binding.answerImage.setImageBitmap(commentList[position].commentUri.
            convertToBitmap())
             */
            holder.binding.answerImage.visibility = View.GONE
            holder.binding.answerText.text = commentList[position].commentText
        }
    }

}