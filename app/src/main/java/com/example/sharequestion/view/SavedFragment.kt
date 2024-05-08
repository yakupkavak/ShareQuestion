package com.example.sharequestion.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sharequestion.R
import com.example.sharequestion.adapter.QuestionRowAdapter
import com.example.sharequestion.adapter.SavedQuestionAdapter
import com.example.sharequestion.databinding.FragmentSavedBinding
import com.example.sharequestion.model.Question
import com.example.sharequestion.service.QuestionDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SavedFragment : Fragment() {
    private var _binding: FragmentSavedBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSavedBinding.inflate(inflater, container, false)
        val view = binding.root
        return view    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //get all questions from dao
        CoroutineScope(Dispatchers.IO).launch {
            val dao = QuestionDatabase(requireContext()).questionDao()
            val questionList = dao.getAllQuestions()
            val arrayQuestion = ArrayList<Question>(questionList)
            withContext(Dispatchers.Main){
                val adapter = SavedQuestionAdapter(requireContext(),arrayQuestion)
                binding.savedRecyclerView.layoutManager = LinearLayoutManager(requireContext())
                binding.savedRecyclerView.adapter = adapter
            }
        }
        



    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}