package com.example.sharequestion.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sharequestion.R
import com.example.sharequestion.databinding.QuestionRowBinding
import com.example.sharequestion.model.Comment
import com.example.sharequestion.model.Question
import com.example.sharequestion.util.downloadUrl
import com.example.sharequestion.util.permission
import com.example.sharequestion.util.setImgCorrect
import com.example.sharequestion.view.AddQuestionFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.security.Permission

class QuestionRowAdapter(val context: Context,val permission: permission,val questionList: ArrayList<Question>):
    RecyclerView.Adapter<QuestionRowAdapter.QuestionViewHolder>() {
    class QuestionViewHolder(val binding: QuestionRowBinding): RecyclerView.ViewHolder(binding.root)

   var imgUri = Uri.EMPTY

    //for close and open comments
    var checkNum = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val binding = QuestionRowBinding.inflate(LayoutInflater.from(parent.context))
        return QuestionViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return questionList.size
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        holder.binding.questionImageRow.downloadUrl(questionList[position].questionUri)
        holder.binding.questionTextRow.text = questionList[position].questionText

        //save comment
         holder.binding.questionSaveImg.setOnClickListener {
            TODO("SAVE TO SQL DATABASE")
        }

        //select comment image
        holder.binding.addImageComment.setOnClickListener {
            permission.askPermission(it)
            holder.binding.addImageComment.setImgCorrect()
        }

        //send comment
        holder.binding.sendComment.setOnClickListener {
            val commentText = holder.binding.questionUserComment.text.toString()
            val commentImgUri = imgUri
            val questionID = questionList[position].questionId
            val userComment = Comment(questionID,commentText,commentImgUri.toString())
            permission.addComment(userComment,imgUri)
        }

        //binding the comment recycler view
        CoroutineScope(Dispatchers.IO).launch {
            val myCommentArray = permission.getComments(questionList[position].questionId)
            val commentAdapter= CommentRowAdapter(myCommentArray)
            withContext(Dispatchers.Main){
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

    fun updateUri(uri : Uri){
        imgUri = uri
    }

}