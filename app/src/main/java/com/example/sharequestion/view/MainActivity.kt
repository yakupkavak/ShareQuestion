package com.example.sharequestion.view

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.sharequestion.R
import com.example.sharequestion.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.bottomNavigationMenu.visibility = View.GONE
        binding.bottomNavigationMenu.setOnNavigationItemSelectedListener { item ->
            println(item.itemId)
            when (item.itemId) {
                R.id.main_screen_menu -> {
                    true
                }
                else -> false
            }
        }
    }
}