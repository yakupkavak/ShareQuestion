package com.example.sharequestion.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity("Questions")
data class Question(
    @PrimaryKey
    @ColumnInfo("questionId")
    val questionId :String,
    @ColumnInfo("questionText")
    val questionText: String,
    @ColumnInfo("questionUri")
    val questionUri: String
)