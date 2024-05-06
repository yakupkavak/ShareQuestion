package com.example.sharequestion.util

import android.widget.ImageView
import com.squareup.picasso.Picasso

fun ImageView.downloadUrl(url:String){

    //adding url to imageView
    Picasso.get().load(url).into(this)
}