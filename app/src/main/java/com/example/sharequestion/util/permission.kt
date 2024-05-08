package com.example.sharequestion.util

import android.content.Intent
import android.net.Uri
import android.view.View
import com.example.sharequestion.model.Comment
import com.example.sharequestion.model.Question

interface permission {
    fun registerLauncher()
    fun askPermission(it: View)
    fun addComment(comment:Comment,uri: Uri)

    fun uploadQuestion(question: Question)

    suspend fun getComments(commnetId: String):ArrayList<Comment>
}