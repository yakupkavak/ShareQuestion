package com.example.sharequestion.util

import android.widget.ImageButton
import android.widget.ImageView
import com.example.sharequestion.R
import com.squareup.picasso.Picasso

fun ImageView.downloadUrl(url:String){

    //adding url to imageView
    if (url.isEmpty()){

    }
    else{
        Picasso.get().load(url).into(this)
    }
}
fun ImageButton.setImgCorrect(){
    this.setImageResource(R.drawable.correct)
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