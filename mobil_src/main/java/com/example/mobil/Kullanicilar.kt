package com.example.mobil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference

class Kullanicilar : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var storageRef: StorageReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var userArrayList: ArrayList<Paylasimlar>// User sınıfımzın adı. (data class) user (küçük u ise )
    private lateinit var kullaniciAdapter: kullaniciAdapter  // dusunceAdapter
    private lateinit var db: FirebaseFirestore


    override fun onOptionsItemSelected(item: MenuItem): Boolean { //belirtilen itemi seçersek

        if(item.itemId == R.id.cikis_yap){
            auth.signOut()// çıkış yapma işini hallediyor
            val intent= Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        }else if (item.itemId == R.id.paylasim_yap) {
            val intent = Intent(this, Bilgiler::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kullanicilar)



        recyclerView= findViewById(R.id.recyclerView)
        recyclerView.layoutManager= LinearLayoutManager(this)  //atıl hoca layout manager tanımlamış bağımsız olarak tanımladı
        recyclerView.setHasFixedSize(true)

        userArrayList= arrayListOf()

        kullaniciAdapter= kullaniciAdapter(userArrayList)
        recyclerView.adapter= kullaniciAdapter
        // adaptöre parametre olarak sadece burada kullandığımız userArrayListi verdik. yani bu activity de
        // öceki değer, değişkenlerin adlarını değiştirdik
        // bu sadece burada kullandığımız farklı bir adaptör. Harf değşikliğinin sebebi bu

        EventChangeListener()
    }

    private fun EventChangeListener() {

        auth = Firebase.auth //authenticatina bağlanmamızı sağlar



        db= FirebaseFirestore.getInstance()
        db.collection("Paylasimlar").orderBy("tarih", Query.Direction.DESCENDING).
        addSnapshotListener(object : EventListener<QuerySnapshot> {

            val guncelKullanici= auth.currentUser

            override fun onEvent(
                value: QuerySnapshot?,
                error: FirebaseFirestoreException?
            ) {

                if(error!= null){
                    Log.e("Firestore error",error.message.toString())
                    return

                }

                for(dc: DocumentChange in value?.documentChanges!!){


                    if(dc.type== DocumentChange.Type.ADDED){

                        userArrayList.add(dc.document.toObject(Paylasimlar::class.java))
                    }
                }

                kullaniciAdapter.notifyDataSetChanged()
            }

        })

    }


    }
