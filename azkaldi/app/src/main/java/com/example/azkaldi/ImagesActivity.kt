package com.example.azkaldi

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.azkaldi.databinding.ActivityImagesBinding
import com.google.firebase.firestore.FirebaseFirestore

class ImagesActivity : AppCompatActivity() {


    private lateinit var binding:ActivityImagesBinding
    private lateinit var firebaseFirestore: FirebaseFirestore
    private var mList = mutableListOf<String>()
    private lateinit var adapter: ImagesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_images)

        binding= ActivityImagesBinding.inflate(layoutInflater)

        fun initVars() {
            firebaseFirestore = FirebaseFirestore.getInstance()
            binding.recyclerView.setHasFixedSize(true)
            binding.recyclerView.layoutManager = LinearLayoutManager(this)
            adapter = ImagesAdapter(mList)
            binding.recyclerView.adapter = adapter
        }

        @SuppressLint("NotifyDataSetChanged")
        fun getImages(){
            binding.progressBar.visibility = View.VISIBLE
            firebaseFirestore.collection("images")
                .get().addOnSuccessListener {
                    for(i in it){
                        mList.add(i.data["pic"].toString())
                    }
                    adapter.notifyDataSetChanged()
                    binding.progressBar.visibility = View.GONE
                }
        }
    }
}