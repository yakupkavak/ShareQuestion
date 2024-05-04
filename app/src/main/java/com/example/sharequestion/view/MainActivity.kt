package com.example.sharequestion.view

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sharequestion.R
import com.example.sharequestion.databinding.ActivityMainBinding
import com.example.sharequestion.viewmodel.LogInViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        binding.bottomNavigationMenu.visibility = View.GONE

        binding.bottomNavigationMenu.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.main_screen_menu -> {
                    true
                }
                else -> false
            }
        }
    }
    fun showNavigation(){
        binding.bottomNavigationMenu.visibility = View.VISIBLE
    }

}