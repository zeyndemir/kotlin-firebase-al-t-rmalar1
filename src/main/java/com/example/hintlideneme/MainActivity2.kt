package com.example.hintlideneme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity2 : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth // tekrar tanımlamamız gerekir

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater= menuInflater
        menuInflater.inflate(R.menu.ana_menu,menu)
        return super.onCreateOptionsMenu(menu)// menu oluşturduk
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean { //belirtilen itemi seçersek

        if(item.itemId == R.id.cikis_yap){
            auth.signOut()// çıkış yapma işini hallediyor
            val intent= Intent(this,login::class.java)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        auth= Firebase.auth
    }
}