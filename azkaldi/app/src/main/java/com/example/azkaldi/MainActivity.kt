package com.example.azkaldi

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.azkaldi.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var storageRef: StorageReference
    private lateinit var firestore: FirebaseFirestore
    private var resimUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initVars()
        registerClickEvents()
    }

    private fun registerClickEvents() {

        binding.btnyukle.setOnClickListener {
            uploadImage()

        }

        binding.btnsec.setOnClickListener {

            startActivity(Intent(this, ImagesActivity::class.java))
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

        binding.progressBar2.visibility= View.VISIBLE
        storageRef = storageRef.child(System.currentTimeMillis().toString())
        resimUri?.let {
            storageRef.putFile(it).addOnCompleteListener {task ->

                if(task.isSuccessful){

                    storageRef.downloadUrl.addOnSuccessListener{ uri->
                        val bilgiMap= hashMapOf(
                            "pic" to uri
                        )

                        firestore.collection("ımages").document().set(bilgiMap).addOnCompleteListener {firestoreTask ->
                            if(firestoreTask.isSuccessful){

                                Toast.makeText(this,"yuklendi", Toast.LENGTH_SHORT).show()

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
