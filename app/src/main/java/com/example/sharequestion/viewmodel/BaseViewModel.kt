package com.example.sharequestion.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel(application: Application) : AndroidViewModel(application= application), CoroutineScope {

    private val job = Job()

    override val coroutineContext: CoroutineContext

        // Do the job and return to main threat
        get() = job + Dispatchers.Main

    override fun onCleared() {
        super.onCleared()

        //clean the job
        job.cancel()
    }
}