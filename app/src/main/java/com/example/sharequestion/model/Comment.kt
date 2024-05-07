package com.example.sharequestion.model

import android.net.Uri

data class Comment(val mainDocumentId:String,val commentText : String?,val commentUri:Uri)