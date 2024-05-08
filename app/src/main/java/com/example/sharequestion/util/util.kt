package com.example.sharequestion.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.widget.ImageButton
import android.widget.ImageView
import com.example.sharequestion.R
import com.squareup.picasso.Picasso
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.ByteArrayOutputStream

fun ImageView.downloadUrl(url:String){

    //adding url to imageView
    if (url.isEmpty()){

    }
    else{
        Picasso.get().load(url).into(this)
    }
}
//Convert url to bitmap
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
                    //here have a input stream which come's from uri
                    //it convers to string
                    val outputStream = ByteArrayOutputStream()
                    BitmapFactory.decodeStream(it).compress(Bitmap.CompressFormat.JPEG,100,outputStream)
                    val imageBytes = outputStream.toByteArray()
                    return Base64.encodeToString(imageBytes, Base64.DEFAULT)
                }
            }
        }
        return ""
    }
}

fun String.convertToBitmap():Bitmap{
    println("convert to bitmap byte arayi -> " + this)
    val decodeBytes = Base64.decode(this,Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(decodeBytes, 0, decodeBytes.size)
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