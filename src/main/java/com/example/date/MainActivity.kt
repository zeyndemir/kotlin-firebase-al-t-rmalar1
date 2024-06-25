package com.example.date

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.compose.ui.text.intl.Locale
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class MainActivity : AppCompatActivity() {

    val db= Firebase.firestore
    private lateinit var tvDatePicker: TextView
    private lateinit var btnDatePicker: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvDatePicker= findViewById(R.id.tarih)
        btnDatePicker= findViewById(R.id.btn_tarih)
        val butonum= findViewById<Button>(R.id.button)

        val myCalender= Calendar.getInstance()

        val datePicker= DatePickerDialog.OnDateSetListener{ view, year, month, day ->
            myCalender.set(Calendar.YEAR, year)
            myCalender.set(Calendar.MONTH, month)
            myCalender.set(Calendar.DAY_OF_YEAR,day )
            updateLable(myCalender)

        }

        btnDatePicker.setOnClickListener {
            DatePickerDialog(this,datePicker, myCalender.get(Calendar.YEAR),myCalender.get(Calendar.MONTH),
                myCalender.get(Calendar.DAY_OF_MONTH)).show()


        }

        butonum.setOnClickListener {
            val bilgimap= hashMapOf(
                "sdf" to myCalender
            )

            db.collection("Date").document().set(bilgimap)
                .addOnSuccessListener {
                    Toast.makeText(this,"oldu", Toast.LENGTH_LONG).show()
                }.addOnFailureListener { exception ->
                    Toast.makeText(applicationContext,exception.localizedMessage,Toast.LENGTH_LONG).show()
                }
        }
    }


    private fun updateLable(myCalender: Calendar) {

        val myFormat= "dd-MM-yyyy"
        val sdf= SimpleDateFormat(myFormat, java.util.Locale.UK)
        tvDatePicker.setText(sdf.format(myCalender.time))


    }
}











