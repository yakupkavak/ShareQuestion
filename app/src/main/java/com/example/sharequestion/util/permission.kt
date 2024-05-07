package com.example.sharequestion.util

import android.content.Intent
import android.net.Uri
import android.view.View
import com.example.sharequestion.model.Comment

interface permission {
    fun registerLauncher()
    fun askPermission(it: View)
    fun addComment(comment:Comment,uri: Uri)

    suspend fun getComments(commnetId: String):ArrayList<Comment>
}