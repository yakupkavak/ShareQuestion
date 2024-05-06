package com.example.sharequestion.util

import android.widget.ImageView
import com.squareup.picasso.Picasso

fun ImageView.downloadUrl(url:String){

    //adding url to imageView
    Picasso.get().load(url).into(this)
}
object userMail{
    var userMail : String? = null

    fun setMail(mail : String){
        userMail = mail
    }
    fun getMail():String?{
        return userMail
    }
}