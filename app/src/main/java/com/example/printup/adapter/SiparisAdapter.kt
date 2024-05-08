package com.example.printup.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.printup.databinding.SiparisRowBinding
import com.example.printup.databinding.TablolarRowBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

class SiparisAdapter(val siparisList:ArrayList<Siparis>): RecyclerView.Adapter<SiparisAdapter.SiparisHolder>() {
    class SiparisHolder(val binding: SiparisRowBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SiparisHolder {
        val binding= SiparisRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SiparisHolder(binding)
    }

    override fun onBindViewHolder(holder: SiparisHolder, position: Int) {
        holder.binding.noText.text=siparisList.get(position).siparisNo.toString()

    }

    override fun getItemCount(): Int {
        return siparisList.size
    }

}