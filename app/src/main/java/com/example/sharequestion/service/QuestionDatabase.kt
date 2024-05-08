package com.example.sharequestion.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.sharequestion.model.Comment
import com.example.sharequestion.model.Question

@Database(entities = [Comment::class,Question::class], version = 1)
abstract class QuestionDatabase: RoomDatabase() {
    abstract fun questionDao() : DaoInterface

    //optimize threads by singleton
    companion object{
        @Volatile private var instance : QuestionDatabase? = null

        private val lock = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            //senkronize olarak çalışacağını belirtiyoruz synchronized ile
            //sonra bunu database ile eşitliyoruz
            instance ?: makeDatabase(context).also{
                instance = it
            }
        }
        
        private fun makeDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext, QuestionDatabase::class.java,"questiondatabase")
            .build()
    }
}