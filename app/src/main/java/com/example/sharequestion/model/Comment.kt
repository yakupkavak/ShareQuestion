package com.example.sharequestion.model

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("Comments")
data class Comment(
    @ColumnInfo("mainDocumentId")
    val mainDocumentId:String,
    @ColumnInfo("commentText")
    val commentText : String?,
    @ColumnInfo("commentUri")
    val commentUri:String)
{
    @PrimaryKey(autoGenerate = true)
    var uuid = 0 //bu constructor içinde olmadıgı için tek tek tanımlaması yapılıyor
}