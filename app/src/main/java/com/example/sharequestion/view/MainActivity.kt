package com.example.sharequestion.view

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
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

            val managerFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView2)
                    as NavHostFragment
            val currentFragment =  managerFragment.childFragmentManager.primaryNavigationFragment
            val myFragment = currentFragment!!
            when (item.itemId) {

                R.id.main_screen_menu -> {
                    if (myFragment is AddQuestionFragment){
                        val action = AddQuestionFragmentDirections.
                        actionAddQuestionFragmentToFeedFragment()
                        Navigation.findNavController(binding.fragmentContainerView2).navigate(action)
                    }
                    else if(myFragment is ChangePasswordFragment){
                        val action = ChangePasswordFragmentDirections.
                        actionChangePasswordFragmentToFeedFragment()
                        Navigation.findNavController(binding.fragmentContainerView2).navigate(action)
                    }
                    else if(myFragment is SavedFragment ){
                        val action = SavedFragmentDirections.actionSavedFragmentToFeedFragment()
                        Navigation.findNavController(binding.fragmentContainerView2).navigate(action)
                    }
                    true
                }

                R.id.add_question_menu ->{
                    if (myFragment is FeedFragment){
                        val action = FeedFragmentDirections.actionFeedFragmentToAddQuestionFragment()
                        Navigation.findNavController(binding.fragmentContainerView2).navigate(action)
                    }
                    else if(myFragment is ChangePasswordFragment){
                        val action = ChangePasswordFragmentDirections.
                        actionChangePasswordFragmentToAddQuestionFragment()
                        Navigation.findNavController(binding.fragmentContainerView2).navigate(action)
                    }
                    else if(myFragment is SavedFragment ){
                        val action = SavedFragmentDirections.actionSavedFragmentToAddQuestionFragment()
                        Navigation.findNavController(binding.fragmentContainerView2).navigate(action)
                    }
                    true
                }

                R.id.change_password_menu ->{
                    if (myFragment is FeedFragment){
                        val action = FeedFragmentDirections.actionFeedFragmentToChangePasswordFragment()
                        Navigation.findNavController(binding.fragmentContainerView2).navigate(action)
                    }
                    else if(myFragment is AddQuestionFragment){
                        val action = AddQuestionFragmentDirections.
                        actionAddQuestionFragmentToChangePasswordFragment()
                        Navigation.findNavController(binding.fragmentContainerView2).navigate(action)
                    }
                    else if(myFragment is SavedFragment ){
                        val action = SavedFragmentDirections.
                        actionSavedFragmentToChangePasswordFragment()
                        Navigation.findNavController(binding.fragmentContainerView2).navigate(action)
                    }
                    true
                }

                R.id.saved_menu ->{
                    if (myFragment is FeedFragment){
                        val action = FeedFragmentDirections.actionFeedFragmentToSavedFragment()
                        Navigation.findNavController(binding.fragmentContainerView2).navigate(action)
                    }
                    else if(myFragment is ChangePasswordFragment){
                        val action = ChangePasswordFragmentDirections.
                        actionChangePasswordFragmentToSavedFragment()
                        Navigation.findNavController(binding.fragmentContainerView2).navigate(action)
                    }
                    else if(myFragment is AddQuestionFragment ){
                        val action = AddQuestionFragmentDirections.
                        actionAddQuestionFragmentToSavedFragment()
                        Navigation.findNavController(binding.fragmentContainerView2).navigate(action)
                    }
                    true
                }

                R.id.logout_menu ->{
                    if (myFragment is FeedFragment){
                        val action = FeedFragmentDirections.actionFeedFragmentToLogInFragment()
                        Navigation.findNavController(binding.fragmentContainerView2).navigate(action)
                        hideNavigation()
                    }
                    else if(myFragment is ChangePasswordFragment){
                        val action = ChangePasswordFragmentDirections.
                        actionChangePasswordFragmentToLogInFragment()
                        Navigation.findNavController(binding.fragmentContainerView2).navigate(action)
                        hideNavigation()
                    }
                    else if(myFragment is SavedFragment ){
                        val action = SavedFragmentDirections.actionSavedFragmentToLogInFragment()
                        Navigation.findNavController(binding.fragmentContainerView2).navigate(action)
                        hideNavigation()
                    }
                    else if(myFragment is AddQuestionFragment ){
                        val action = AddQuestionFragmentDirections.
                        actionAddQuestionFragmentToLogInFragment()
                        Navigation.findNavController(binding.fragmentContainerView2).navigate(action)
                        hideNavigation()
                    }
                    true
                }



                else -> false
            }
        }
    }
    fun showNavigation(){
        binding.bottomNavigationMenu.visibility = View.VISIBLE
    }
    fun hideNavigation(){
        binding.bottomNavigationMenu.visibility = View.GONE
    }

}