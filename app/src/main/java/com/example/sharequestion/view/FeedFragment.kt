package com.example.sharequestion.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sharequestion.R
import com.example.sharequestion.adapter.QuestionRowAdapter
import com.example.sharequestion.databinding.FragmentFeedBinding
import com.example.sharequestion.model.Question
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class FeedFragment : Fragment() {
    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!
    private lateinit var fireDatabase : FirebaseFirestore
    private lateinit var adapter : QuestionRowAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        val view = binding.root
        return view    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fireDatabase = Firebase.firestore
        var myDownloadArray = ArrayList<Question>()

        //binding adapter with firebase
        CoroutineScope(Dispatchers.IO).launch {
            myDownloadArray = getDataFromFirebase()
            adapter = QuestionRowAdapter(myDownloadArray)
            withContext(Dispatchers.Main){
                binding.feedRecyclerView.layoutManager = LinearLayoutManager(requireContext())
                binding.feedRecyclerView.adapter = adapter
            }
        }
    }
    suspend fun getDataFromFirebase():ArrayList<Question>{
        val questionArray = ArrayList<Question>()
        val dbRef = fireDatabase.collection("questions")
        val dbList = dbRef.get().await()
        for (i in dbList){
            val data = i.data
            questionArray.add(Question(i.id,
                data["comment"].toString(),
                data["imageUri"].toString()))
        }

        return questionArray
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}