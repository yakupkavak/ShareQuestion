package com.example.sharequestion.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.sharequestion.databinding.QuestionRowBinding
import com.example.sharequestion.model.Comment
import com.example.sharequestion.model.Question
import com.example.sharequestion.service.QuestionDatabase
import com.example.sharequestion.util.convertToBitmap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SavedQuestionAdapter(val context: Context,val questionList:ArrayList<Question>): Adapter<SavedQuestionAdapter.SavedViewHolder>() {

    class SavedViewHolder(val binding : QuestionRowBinding): ViewHolder(binding.root)
    var checkNum = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedViewHolder {
        val binding = QuestionRowBinding.inflate(LayoutInflater.from(parent.context))
        return SavedViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return questionList.size
    }

    override fun onBindViewHolder(holder: SavedViewHolder, position: Int) {
        //close the save button
        holder.binding.questionSaveImg.visibility = View.GONE

        //can't add comment to saved
        holder.binding.questionRowLinear.visibility = View.GONE

        //question text
        holder.binding.questionTextRow.text = questionList[position].questionText

        //if there is no image make it invisible
        if (questionList[position].questionUri == ""){
            holder.binding.questionImageRow.visibility = View.GONE
        }
        else{
            holder.binding.questionImageRow.visibility = View.VISIBLE
            holder.binding.questionImageRow.setImageBitmap(questionList[position].questionUri.
            convertToBitmap())
        }

        //implement comments
        CoroutineScope(Dispatchers.IO).launch {
            val dao = QuestionDatabase(context).questionDao()
            val myCommentArray = dao.getAllComments(questionList[position].questionId)
            val arrayComment = ArrayList<Comment>(myCommentArray)

            withContext(Dispatchers.Main){
                val commentAdapter= CommentRowAdapter(arrayComment)
                holder.binding.commentRecyclerView.layoutManager = LinearLayoutManager(context)
                holder.binding.commentRecyclerView.adapter = commentAdapter
                if (myCommentArray.isEmpty()){
                    holder.binding.showCommentsText.visibility = View.GONE
                }
                else{
                    holder.binding.showCommentsText.visibility = View.VISIBLE
                }
            }
        }


        //make visible and invisible comments
        holder.binding.commentRecyclerView.visibility = View.GONE
        holder.binding.showCommentsText.setOnClickListener {
            if (checkNum == 0){
                holder.binding.commentRecyclerView.visibility = View.VISIBLE
                holder.binding.showCommentsText.text = "Hide comments"
                checkNum = 1
            }
            else{
                holder.binding.commentRecyclerView.visibility = View.GONE
                holder.binding.showCommentsText.text = "Show comments"
                checkNum = 0
            }
        }


    }
}