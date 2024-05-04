package com.example.sharequestion.viewmodel

import android.app.Application
import android.nfc.Tag
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class LogInViewModel(application: Application) : BaseViewModel(application) {

    private lateinit var auth: FirebaseAuth

    //user sign up with auth
    fun signUp(email : String, password: String, callback : (Boolean)-> Unit){
        auth = Firebase.auth

        //control the it it worked or not with callback
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {task ->
            if (task.isSuccessful){
                println("User Saved")
                callback(true)
            }
        }.addOnFailureListener {
            println(it.localizedMessage)
            callback(false)
        }

    }

    fun signIn(email : String, password: String, callback : (Boolean)-> Unit){
        auth = Firebase.auth

        //control the it it worked or not with callback
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {task ->
            if (task.isSuccessful){
                println("User sign in")
                callback(true)
            }
        }.addOnFailureListener {
            println(it.localizedMessage)
            callback(false)
        }

    }




}