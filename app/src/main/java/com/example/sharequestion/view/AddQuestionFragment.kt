package com.example.sharequestion.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sharequestion.R
import com.example.sharequestion.databinding.FragmentAddQuestionBinding

class AddQuestionFragment : Fragment() {
    private var _binding: FragmentAddQuestionBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddQuestionBinding.inflate(inflater, container, false)
        val view = binding.root
        return view    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}