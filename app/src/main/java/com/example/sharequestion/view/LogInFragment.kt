package com.example.sharequestion.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.sharequestion.R
import com.example.sharequestion.databinding.FragmentLogInBinding
import com.example.sharequestion.viewmodel.LogInViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class LogInFragment : Fragment() {
    private var _binding: FragmentLogInBinding? = null
    private val binding get() = _binding!!
    private var email :String? = null
    private var password :String? = null
    private lateinit var viewModel : LogInViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLogInBinding.inflate(inflater, container, false)
        val view = binding.root
        return view    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(LogInViewModel::class.java)
        val action = LogInFragmentDirections.actionLogInFragmentToFeedFragment()

        //show navigation with live data
        viewModel.logInSuccess.observe(viewLifecycleOwner){
            val activity = activity as MainActivity
            activity.showNavigation()
        }

        binding.signInButton.setOnClickListener {
            //firstly check empty and update email and password.
            if (getInformation()){
                viewModel.signIn(email!!,password!!){callback ->
                    if (callback){
                        Navigation.findNavController(it).navigate(action)

                    }
                    else{
                        Toast.makeText(requireContext(),"User couldn't found!",
                            Toast.LENGTH_LONG).show()
                    } }
            }
        }
        binding.signUpButton.setOnClickListener {
            //firstly check empty and update email and password.
            if (getInformation()){
                viewModel.signUp(email!!,password!!){callback ->
                    if (callback){
                        Navigation.findNavController(it).navigate(action)
                    }
                    else{
                        Toast.makeText(requireContext(),"There is an error!",
                            Toast.LENGTH_LONG).show()
                    } }
            }
        }

    }

    fun getInformation():Boolean{ //it checks email or password is empty. And it implements values
        if (binding.emailEditText.text.toString().isEmpty() ||
            binding.passwordEditText.text.toString().isEmpty()){
            Toast.makeText(requireContext(),"E-mail or Password is empty!",Toast.LENGTH_LONG)
                .show()
            return false
        }
        else{
            email = binding.emailEditText.text.toString()
            password = binding.passwordEditText.text.toString()
            return true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}