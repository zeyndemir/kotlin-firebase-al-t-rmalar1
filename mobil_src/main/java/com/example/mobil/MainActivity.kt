package com.example.mobil

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        auth = Firebase.auth //authenticatina bağlanmamızı sağlar

        val guncelKullanici= auth.currentUser //currentuser oluşturup guncelkullanıcı olarak isimlendirdik

        if ( guncelKullanici != null) { //guncelkullanıcı önceden girilmişse yani boş değilse direk bir sonraki sayfaya geç
            val intent= Intent(this,Bilgiler::class.java)
            startActivity(intent)
            finish()
        }

        var emailtext=findViewById<EditText>(R.id.mailtext)
        var sifretext=findViewById<EditText>(R.id.sifretext)
        var kullaniciAditext=findViewById<EditText>(R.id.kullaniciAditext)
        var btn_kaydol=findViewById<Button>(R.id.btn_kaydol)
        var btn_giris= findViewById<Button>(R.id.btn_giris)
        var tvdevamet= findViewById<TextView>(R.id.tvdevamet)

        btn_kaydol.setOnClickListener {
            var email= emailtext.text.toString()
            var sifre= sifretext.text.toString()
            var kullaniciAdi= kullaniciAditext.text.toString()
            println(emailtext)

            auth.createUserWithEmailAndPassword(email, sifre).addOnCompleteListener { task ->
                if (task.isSuccessful){

                    //Kullanıcı adını güncelle
                    val guncelKullanici =auth.currentUser

                    val profilGuncelleme = userProfileChangeRequest {
                        displayName = kullaniciAdi


                    }

                    if(guncelKullanici != null){//aşağıda !! olayı istenmediğinden ifledik. gerek kalmadı
                        guncelKullanici.updateProfile(profilGuncelleme).addOnCompleteListener { task ->
                            if(task.isSuccessful){}
                            Toast.makeText(applicationContext,"Kullanıcı  Eklendi", Toast.LENGTH_LONG).show()
                        }// iki ünlem bu iki kulanıcı kesinlikle var olacak demek.Kaçınıyoruz
                    }

                    val intent = Intent(this,Bilgiler::class.java)
                    startActivity(intent)
                    finish()
                }
            }.addOnFailureListener { exception ->
                Toast.makeText(applicationContext,exception.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }

        btn_giris.setOnClickListener{

            var email= emailtext.text.toString()
            var sifre= sifretext.text.toString()

            if(sifre != "" && email!= ""){
                auth.signInWithEmailAndPassword(email,sifre).addOnCompleteListener { task ->
                    if (task.isSuccessful){

                        val guncelKullanici= auth.currentUser?.displayName.toString()
                        Toast.makeText(applicationContext,"Hoşgeldin: ${guncelKullanici}", Toast.LENGTH_LONG).show()

                        val intent = Intent(this, Kullanicilar::class.java)
                        startActivity(intent)
                        finish()

                    }

                }.addOnFailureListener { exception ->
                    Toast.makeText(applicationContext,exception.localizedMessage, Toast.LENGTH_LONG).show()
                }
            }



        }

        tvdevamet.setOnClickListener {

            val intent = Intent(this, Kullanicilar::class.java)
            startActivity(intent)
            finish()

        }

    }

    }
