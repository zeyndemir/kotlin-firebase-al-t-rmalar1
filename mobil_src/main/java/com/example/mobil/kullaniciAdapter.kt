package com.example.mobil

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class kullaniciAdapter (private val userList: ArrayList<Paylasimlar>): RecyclerView.Adapter<kullaniciAdapter.kisiHolder>(){

    private var resimUri: Uri? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): kullaniciAdapter.kisiHolder {

        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.list_item,
            parent, false)
        return kisiHolder(itemView)
    }

    override fun onBindViewHolder(holder: kullaniciAdapter.kisiHolder, position: Int) {
        val user: Paylasimlar = userList[position] // atıl hocada userListe=paylasimListesi
        holder.isim.text=user.isim
        holder.soyisim.text=user.soyisim
        holder.tarih.text= user.tarih.toString()
        if(userList[position].pic!= null){
            holder.pic.visibility= View.VISIBLE
            Picasso.get().load(resimUri).into(holder.pic)
        }
    }

    override fun getItemCount(): Int {
        return userList.size

    }

    public class kisiHolder (itemView: View): RecyclerView.ViewHolder(itemView){
        val isim: TextView = itemView.findViewById(R.id.isim)
        val soyisim: TextView = itemView.findViewById(R.id.soyisim)
        val tarih: TextView = itemView.findViewById(R.id.tarih)
        val pic: ImageView= itemView.findViewById(R.id.resim)


    }

}


