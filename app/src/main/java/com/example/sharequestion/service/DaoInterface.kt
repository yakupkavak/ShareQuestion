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

    @Query("DELETE FROM Questions")
    fun deleteAllQuestions()

    @Query("DELETE FROM Comments")
    fun deleteAllComments()

    @Query("SELECT * FROM Questions WHERE questionId = :checkId")
    fun checkQuestion(checkId: String): Question?

    @Insert
    fun insertQuestion(vararg questions: Question)
    @Insert
    fun insertComment(vararg comments: Comment)



}