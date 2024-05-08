package com.example.sharequestion.view

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sharequestion.adapter.QuestionRowAdapter
import com.example.sharequestion.databinding.FragmentFeedBinding
import com.example.sharequestion.model.Comment
import com.example.sharequestion.model.Question
import com.example.sharequestion.util.permission
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.UUID

class FeedFragment : Fragment() ,permission{
    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!
    private lateinit var fireDatabase : FirebaseFirestore
    private lateinit var fireStorage: FirebaseStorage
    private lateinit var adapter : QuestionRowAdapter
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private lateinit var intentGallery : Intent
    private lateinit var imgUri: Uri
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
        registerLauncher()
        intentGallery = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.
        EXTERNAL_CONTENT_URI)

        fireDatabase = Firebase.firestore
        fireStorage = Firebase.storage

        //binding adapter with firebase
        CoroutineScope(Dispatchers.Default).launch {
            refreshFeed()
        }

    }
    //refresh the recyclerView with firebase
    suspend fun refreshFeed(){
        CoroutineScope(Dispatchers.IO).launch {
            val myDownloadArray = getDataFromFirebase()
            adapter = QuestionRowAdapter(requireContext(),this@FeedFragment,myDownloadArray)
            withContext(Dispatchers.Main){
                binding.feedRecyclerView.layoutManager = LinearLayoutManager(requireContext())
                binding.feedRecyclerView.adapter = adapter
            }
        }
    }

    //get data from question collection
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

    //add comment to using documentId
    override fun addComment(comment:Comment,uri:Uri){
        //uri boş geldi empty
        fireDatabase
        val storageRef = fireStorage.reference

        //create unique uuid and image name.
        val uuid = UUID.randomUUID().toString()
        val imageRef = storageRef.child("commentsImages/$uuid.jpg")
        if (uri == Uri.EMPTY){
            val userComment = hashMapOf(
                "commentUrl" to "",
                "userComment" to comment.commentText,
            )

            //add it to question id location
            fireDatabase.document("questions/${comment.mainDocumentId}")
                .collection("comments")
                .add(userComment)
                .addOnSuccessListener {
                    //refresh the feed screen with new comment
                    CoroutineScope(Dispatchers.Default).launch {
                        refreshFeed()
                    }
                }.addOnFailureListener {
                    println(it.localizedMessage)
                }
        }
        else{
            imageRef.putFile(uri).addOnCompleteListener {
                //get url and text to firebase
                imageRef.downloadUrl.addOnCompleteListener{commentUrl->
                    val userComment = hashMapOf(
                        "commentUrl" to commentUrl.result.toString(),
                        "userComment" to comment.commentText,
                    )

                    //add it to question id location
                    fireDatabase.document("questions/${comment.mainDocumentId}")
                        .collection("comments")
                        .add(userComment)
                        .addOnSuccessListener {
                            //refresh the feed screen with new comment
                            CoroutineScope(Dispatchers.Default).launch {
                                refreshFeed()
                            }
                        }.addOnFailureListener {
                            println(it.localizedMessage)
                        }
                }
            }
        }
        //add image to firestorage
    }

    //get comment by documentId
    override suspend fun getComments(commnetId: String): ArrayList<Comment> {
        val commentArray = ArrayList<Comment>()
        val dbRef = fireDatabase.document("questions/${commnetId}")
            .collection("comments")
        val dbList = dbRef.get().await()

        for (i in dbList){
            val data = i.data
            commentArray.add(Comment
                (commnetId,
                data["userComment"].toString(),
                data["commentUrl"].toString()))
        }
        return commentArray
    }

    override fun askPermission(it:View){
        if (Build.VERSION.SDK_INT > 32){

            //the permission is granted
            if (ContextCompat.checkSelfPermission(requireContext(),android.Manifest.
                permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED){
                activityResultLauncher.launch(intentGallery)
            }

            //request permission MEDIA_IMAGES
            else{
                if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),
                        android.Manifest.permission.READ_MEDIA_IMAGES)){
                    Snackbar.make(it,"Permission for gallery", Snackbar.LENGTH_LONG).
                    setAction("Give Permission"){
                        permissionLauncher.launch(android.Manifest.permission.READ_MEDIA_IMAGES)
                    }.show()
                }else{
                    permissionLauncher.launch(android.Manifest.permission.READ_MEDIA_IMAGES)
                } }
        }
        else{

            //the permission is granted
            if (ContextCompat.checkSelfPermission(requireContext(),android.Manifest.
                permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                activityResultLauncher.launch(intentGallery)
            }
            //request permission EXTERNAL_STORAGE
            else{
                if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),
                        android.Manifest.permission.READ_MEDIA_IMAGES)){
                    Snackbar.make(it,"Permission for gallery", Snackbar.LENGTH_LONG).
                    setAction("Give Permission"){
                        permissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    }.show()
                }else{
                    permissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                } }
        }

    }
    override fun registerLauncher(){
        activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.
        StartActivityForResult()){result->
            //check activity worked
            if (result.resultCode == Activity.RESULT_OK){
                val dataFromResult = result.data
                dataFromResult?.let {
                    val imageData = it.data
                    imageData?.let { uri ->  //uri is the data of the intent.
                        adapter.updateUri(uri)
                    }
                }
            }
        }
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.
        RequestPermission()){
            if (it){
                //have permission and intent to gallery
                val intentGallery = Intent(
                    Intent.ACTION_PICK, MediaStore.Images.Media.
                EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intentGallery)
            }
            else{
                //doesn't have permission
                Toast.makeText(requireContext(),"Give Permission", Toast.LENGTH_LONG).show()
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}