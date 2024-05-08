package com.example.printup.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.printup.databinding.AlisVerisRowBinding

class AlisVerisSepetiAdapter(val alisVerisList:ArrayList<Sepet>): RecyclerView.Adapter<AlisVerisSepetiAdapter.AlisVerisHolder>() {
    class AlisVerisHolder(val binding: AlisVerisRowBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlisVerisHolder {

        val binding= AlisVerisRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AlisVerisHolder(binding)
    }

    override fun onBindViewHolder(holder: AlisVerisHolder, position: Int) {

        holder.binding.tabloAd.text=alisVerisList.get(position).tabload
        holder.binding.fiyatText.text=alisVerisList.get(position).fiyat
        holder.binding.boyutText.text=alisVerisList.get(position).boyut
        holder.binding.cerceveText.text=alisVerisList.get(position).cerceve


    }

    override fun getItemCount(): Int {
        return alisVerisList.size
    }

}