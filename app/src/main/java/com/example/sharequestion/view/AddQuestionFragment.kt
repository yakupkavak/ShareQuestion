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
import com.example.sharequestion.databinding.FragmentAddQuestionBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage
import java.util.UUID

class AddQuestionFragment : Fragment() {
    private var _binding: FragmentAddQuestionBinding? = null
    private val binding get() = _binding!!
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private lateinit var intentGallery : Intent
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerLauncher()

        intentGallery = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.
        EXTERNAL_CONTENT_URI)

        binding.questionImage.setOnClickListener {
            //check sdk for media or external permission
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
                        Snackbar.make(it,"Permission for gallery",Snackbar.LENGTH_LONG).
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
                        Snackbar.make(it,"Permission for gallery",Snackbar.LENGTH_LONG).
                        setAction("Give Permission"){
                            permissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                        }.show()
                    }else{
                        permissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    } }
                }
            }

        binding.addQuestionButtton.setOnClickListener {
        }

        }
    fun uploadFile(uri: Uri){
        val dbFire = Firebase.firestore
        val storageRef = Firebase.storage.reference

        //create unique uuid and image name.
        val uuid = UUID.randomUUID().toString()
        val imageRef = storageRef.child("images/$uuid.jpg")

        //put image to storage
        imageRef.putFile(uri).addOnCompleteListener {
            println("upload successful")
            imageRef.downloadUrl.addOnCompleteListener{downloadUrl ->
                println("download url ->" + downloadUrl.result )

            }
        }.addOnFailureListener {
            println(it.localizedMessage)
        }
    }
    //implement launchers function for permission and intent
    fun registerLauncher(){
        activityResultLauncher = registerForActivityResult(ActivityResultContracts.
        StartActivityForResult()){result->
            //check activity worked
            if (result.resultCode == Activity.RESULT_OK){
                val dataFromResult = result.data
                dataFromResult?.let {
                    val imageData = it.data
                    imageData?.let { uri ->  //uri is the data of the intent.
                        println("The image uri -> "+uri)
                        uploadFile(uri)
                    }
                }
            }
        }
        permissionLauncher = registerForActivityResult(ActivityResultContracts.
        RequestPermission()){
            if (it){
                //have permission and intent to gallery
                val intentGallery = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.
                EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intentGallery)
            }
            else{
                //doesn't have permission
                Toast.makeText(requireContext(),"Give Permission",Toast.LENGTH_LONG).show()
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
