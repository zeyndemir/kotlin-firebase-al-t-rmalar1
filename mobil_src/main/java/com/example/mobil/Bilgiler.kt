package com.example.mobil


import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

class Bilgiler : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var  db: FirebaseFirestore
    private lateinit var tvdt: TextView
    private lateinit var btndt: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bilgiler)

        auth= Firebase.auth
        db= Firebase.firestore

        var tvisim= findViewById<EditText>(R.id.tvisim)
        var tvsoyisim= findViewById<EditText>(R.id.tvsoyisim)
        tvdt= findViewById(R.id.tvdt)
        btndt= findViewById(R.id.btn_tarih)
        var btnsayfa= findViewById<Button>(R.id.btn_sayfa)
        var btnresim= findViewById<Button>(R.id.btn_image)
        var tvdevam= findViewById<TextView>(R.id.textView)

        val takvim= Calendar.getInstance()

        val datePicker= DatePickerDialog.OnDateSetListener{ view, Yil, Ay, Gun ->
            takvim.set(Calendar.YEAR, Yil)
            takvim.set(Calendar.MONTH, Ay)
            takvim.set(Calendar.DAY_OF_YEAR,Gun )
            updateLable(takvim)

        }


        btndt.setOnClickListener {
            DatePickerDialog(this,datePicker, takvim.get(Calendar.YEAR),takvim.get(Calendar.MONTH),
                takvim.get(Calendar.DAY_OF_MONTH)).show()


        }

        btnsayfa.setOnClickListener {
            val kullaniciAdi= auth.currentUser!!.displayName.toString()
            val isim= tvisim.text.toString()
            val soyisim= tvsoyisim.text.toString()

            val bilgiMap= hashMapOf(
                "kullaniciAdi" to kullaniciAdi,
                "isim" to isim,
                "soyisim" to soyisim,
                "tarih" to takvim
            )
            db.collection("Paylasimlar").document().set(bilgiMap).addOnCompleteListener {task ->
                if(task.isSuccessful){

                    Toast.makeText(this,"Yüklendi", Toast.LENGTH_LONG).show()
                    val intent= Intent(this, Kullanicilar::class.java)
                    startActivity(intent)
                    finish()
                }

            }.addOnFailureListener { exception ->
                Toast.makeText(this,exception.localizedMessage, Toast.LENGTH_LONG).show()
            }

        }

        tvdevam.setOnClickListener {

            val intent= Intent(this, Kullanicilar::class.java)
            startActivity(intent)
            finish()

        }

        btnresim.setOnClickListener {
            val intent= Intent(this,image::class.java)
            startActivity(intent)

        }

    }




    private fun updateLable(takvim: Calendar){

        val myFormat= "dd-MM-yyyy"
        val sdf= SimpleDateFormat(myFormat, Locale.UK)
        tvdt.setText(sdf.format(takvim.time))

    }


}