package com.example.mobil

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.mobil.R
import com.example.mobil.databinding.ActivityImageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class image : AppCompatActivity() {

    private lateinit var binding: ActivityImageBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var storageRef: StorageReference
    private lateinit var firestore: FirebaseFirestore
    private var resimUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initVars()
        registerClickEvents()
    }

    private fun registerClickEvents() {


        binding.btnyukle.setOnClickListener {
            uploadImage()

        }


        binding.resim.setOnClickListener {
            resultLauncher.launch("image/*")

        }

    }

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) {

        resimUri = it
        binding.resim.setImageURI(it)

    }

    private fun initVars() {
        storageRef = FirebaseStorage.getInstance().reference.child("Images")
        firestore = FirebaseFirestore.getInstance()
    }

    private fun uploadImage() {

        var bar= findViewById<ProgressBar>(R.id.progressBar2)


        storageRef = storageRef.child(System.currentTimeMillis().toString())
        resimUri?.let {
            storageRef.putFile(it).addOnCompleteListener {task ->

                if(task.isSuccessful){

                    storageRef.downloadUrl.addOnSuccessListener{ uri->
                        val bilgiMap= hashMapOf(
                            "pic" to uri
                        )

                        firestore.collection("Paylasimlar").document().set(bilgiMap).addOnCompleteListener {firestoreTask ->
                            if(firestoreTask.isSuccessful){



                                Toast.makeText(this,"yuklendi", Toast.LENGTH_SHORT).show()
                                Handler().postDelayed({

                                    val intent = Intent(this, Bilgiler::class.java)
                                    startActivity(intent)
                                    finish()

                                },3000)



                            }else{

                                Toast.makeText(this,firestoreTask.exception?.localizedMessage, Toast.LENGTH_SHORT).show()
                            }


                            binding.progressBar2.visibility= View.GONE
                            binding.resim.setImageResource(R.drawable.profile)
                        }

                    }

                }else {
                    Toast.makeText(this,task.exception?.localizedMessage, Toast.LENGTH_SHORT).show()
                    binding.progressBar2.visibility= View.GONE
                    binding.resim.setImageResource(R.drawable.profile)



                }

            }
        }
    }
}
