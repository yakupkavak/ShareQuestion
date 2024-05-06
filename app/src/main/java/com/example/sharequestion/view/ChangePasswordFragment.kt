package com.example.sharequestion.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.sharequestion.R
import com.example.sharequestion.databinding.FragmentChangePasswordBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.auth

class ChangePasswordFragment : Fragment() {
    private var _binding: FragmentChangePasswordBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentChangePasswordBinding.inflate(inflater, container, false)
        val view = binding.root
        return view    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val firebaseAuth = Firebase.auth
        val user = firebaseAuth.currentUser

        binding.changeButton.setOnClickListener {view->
            val credential = EmailAuthProvider
                .getCredential(user?.email!!,binding.pastPassword.text.toString())

            user.reauthenticate(credential).addOnSuccessListener{
                if (binding.newPassword.text.toString() == binding.newPasswordAgain.text.toString()){
                    val newPassword = binding.newPassword.text.toString()
                    user!!.updatePassword(newPassword).addOnSuccessListener {
                        Toast.makeText(requireContext(),"Password Changed",Toast.LENGTH_LONG).show()
                    }.addOnFailureListener {
                        Toast.makeText(requireContext(),"There is an error.",Toast.LENGTH_LONG).show()
                    }

                    val action = ChangePasswordFragmentDirections.actionChangePasswordFragmentToLogInFragment()
                    Navigation.findNavController(view).navigate(action)
                }
                else{
                    Toast.makeText(requireContext(),"Not equal passwords",Toast.LENGTH_LONG).show()
                }
            }.addOnFailureListener {
                Toast.makeText(requireContext(),"Wrong Password!",Toast.LENGTH_LONG).show()
            }

        }











    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}