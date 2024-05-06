package com.example.sharequestion.util

import android.content.Intent
import android.view.View

interface permission {
    fun registerLauncher()
    fun askPermission(it: View)
}