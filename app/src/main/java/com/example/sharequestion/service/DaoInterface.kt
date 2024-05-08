package com.example.sharequestion.service

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.sharequestion.model.Comment
import com.example.sharequestion.model.Question

@Dao
interface DaoInterface {
    @Query("SELECT * FROM Questions")
    fun getAllQuestions(): List<Question>

    @Query("SELECT * FROM Comments WHERE mainDocumentId = :mainDocId ")
    fun getAllComments(mainDocId : String): List<Comment>

    @Insert(entity = Question::class)
    fun insertQuestions(vararg questions: Question)
    @Insert
    fun insertComments(vararg comments: Comment)

}