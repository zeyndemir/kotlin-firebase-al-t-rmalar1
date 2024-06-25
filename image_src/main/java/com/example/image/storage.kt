package com.example.image

import android.annotation.SuppressLint
import android.app.Instrumentation.ActivityResult
import android.media.Image
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class storage : AppCompatActivity() {

    private lateinit var  resim: ImageView
    private lateinit var  btnsec: Button
    private lateinit var  btnyukle: Button

    private lateinit var storageReference: FirebaseStorage
    private lateinit var uri: Uri

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storage)

        storageReference= FirebaseStorage.getInstance()

        resim= findViewById(R.id.resim)
        btnsec= findViewById(R.id.btnsec)
        btnyukle= findViewById(R.id.btnyukle)

        val galeryresim= registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback {
                resim.setImageURI(it)
                if (it != null) {
                    uri= it
                }
            }
        )

        btnsec.setOnClickListener {
            galeryresim.launch("resim/*")
        }

        btnyukle.setOnClickListener {

            storageReference.getReference("Images").child(System.currentTimeMillis().toString())
                .putFile(uri)
                .addOnSuccessListener { task ->
                    task.metadata?.reference!!.downloadUrl
                        .addOnSuccessListener {
                            val databaseReference= FirebaseDatabase.getInsta
                        }
                }
        }

    }
}