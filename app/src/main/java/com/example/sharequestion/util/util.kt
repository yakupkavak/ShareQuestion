package com.example.sharequestion.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageButton
import android.widget.ImageView
import com.example.sharequestion.R
import com.squareup.picasso.Picasso
import okhttp3.OkHttpClient
import okhttp3.Request

fun ImageView.downloadUrl(url:String){

    //adding url to imageView
    if (url.isEmpty()){

    }
    else{
        Picasso.get().load(url).into(this)
    }
}

fun String.convertBitmap():String{
    val myString = this
    if (myString == ""){
        return ""
    }
    else{
        val client = OkHttpClient()
        val request = Request.Builder().url(this).build()
        client.newCall(request).execute().use { response ->
            if (response.isSuccessful){
                response.body?.byteStream()?.let {
                    return BitmapFactory.decodeStream(it).toString()
                }
            }


        }
        return ""
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