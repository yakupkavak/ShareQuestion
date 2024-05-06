package com.example.sharequestion.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.sharequestion.databinding.QuestionRowBinding
import com.example.sharequestion.model.Question
import com.example.sharequestion.util.downloadUrl
import com.example.sharequestion.util.permission
import com.example.sharequestion.view.AddQuestionFragment
import com.google.android.material.snackbar.Snackbar
import java.security.Permission

class QuestionRowAdapter(val permission: permission,val questionList: ArrayList<Question>): RecyclerView.Adapter<QuestionRowAdapter.QuestionViewHolder>() {
    class QuestionViewHolder(val binding: QuestionRowBinding): RecyclerView.ViewHolder(binding.root)

    /*
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private lateinit var intentGallery : Intent

     */

    private lateinit var imgUri: Uri

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val binding = QuestionRowBinding.inflate(LayoutInflater.from(parent.context))
        return QuestionViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return questionList.size
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        //registerLauncher()

        /*
        val intentGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.
        EXTERNAL_CONTENT_URI)
        */


        holder.binding.questionImageRow.downloadUrl(questionList[position].questionUri)
        holder.binding.questionTextRow.text = questionList[position].questionText

        holder.binding.addImageComment.setOnClickListener {
            //save image
             println("question")
            permission.askPermission(it)
        }

        holder.binding.addComment.setOnClickListener {
            val commentText = holder.binding.questionUserComment.text.toString()

        }
        //make visible comments
        holder.binding.showCommentsText.setOnClickListener {
            holder.binding.questionRecyclerView.visibility = View.VISIBLE
        }


    }
    /*
    fun registerLauncher(){
        activityResultLauncher = activity.registerForActivityResult(
            ActivityResultContracts.
        StartActivityForResult()){result->
            //check activity worked
            if (result.resultCode == Activity.RESULT_OK){
                val dataFromResult = result.data
                dataFromResult?.let {
                    val imageData = it.data
                    imageData?.let { uri ->  //uri is the data of the intent.
                        imgUri = uri
                    }
                }
            }
        }
        permissionLauncher = activity.registerForActivityResult(
            ActivityResultContracts.
        RequestPermission()){
            if (it){
                //have permission and intent to gallery
                val intentGallery = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.
                EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intentGallery)
            }
            else{
                //doesn't have permission
                Toast.makeText(context,"Give Permission", Toast.LENGTH_LONG).show()
            }
        }
    }
    fun askPermission(it:View){
        if (Build.VERSION.SDK_INT > 32){

            //the permission is granted
            if (ContextCompat.checkSelfPermission(context,android.Manifest.
                permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED){

                activityResultLauncher.launch(intentGallery)
            }

            //request permission MEDIA_IMAGES
            else{
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
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
            if (ContextCompat.checkSelfPermission(context,android.Manifest.
                permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                activityResultLauncher.launch(intentGallery)
            }

            //request permission EXTERNAL_STORAGE
            else{
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
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

     */

}