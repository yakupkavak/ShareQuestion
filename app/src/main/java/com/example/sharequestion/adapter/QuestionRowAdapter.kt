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
import androidx.recyclerview.widget.RecyclerView
import com.example.sharequestion.databinding.QuestionRowBinding
import com.example.sharequestion.model.Question
import com.example.sharequestion.util.downloadUrl
import com.example.sharequestion.util.permission
import com.example.sharequestion.view.AddQuestionFragment
import com.google.android.material.snackbar.Snackbar
import java.security.Permission

class QuestionRowAdapter(val permission: permission,val questionList: ArrayList<Question>): RecyclerView.Adapter<QuestionRowAdapter.QuestionViewHolder>() {
    class QuestionViewHolder(val binding: QuestionRowBinding): RecyclerView.ViewHolder(binding.root)

   var imgUri = Uri.EMPTY

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

        holder.binding.addImageComment.setOnClickListener {
            //save image
            permission.askPermission(it)
        }
        holder.binding.questionSaveImg.setOnClickListener {
            println("my selected uri $imgUri")
        }

        holder.binding.addComment.setOnClickListener {
            val commentText = holder.binding.questionUserComment.text.toString()

        }
        //make visible comments
        holder.binding.showCommentsText.setOnClickListener {
            holder.binding.questionRecyclerView.visibility = View.VISIBLE
        }


    }
    fun updateUri(uri : Uri){
        println("uri updated to" + uri)
        imgUri = uri
    }

}