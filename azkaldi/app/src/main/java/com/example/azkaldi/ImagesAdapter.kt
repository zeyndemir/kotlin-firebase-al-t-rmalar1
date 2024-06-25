package com.example.azkaldi

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ImagesAdapter(private var mList: List<String>) :
    RecyclerView.Adapter<ImagesAdapter.ImagesViewHolder>() {

        inner class ImagesViewHolder(var binding: liste_itemBinding) :
            RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesViewHolder {
            val itemView= LayoutInflater.from(parent.context).inflate(R.layout.liste_item,parent, false))
            return ImagesViewHolder(mList)
        }

        override fun onBindViewHolder(holder: ImagesViewHolder, position: Int) {
            val mylist= mList[position]
            holder.
            }
        }

        override fun getItemCount(): Int {
            return mList.size
        }
}