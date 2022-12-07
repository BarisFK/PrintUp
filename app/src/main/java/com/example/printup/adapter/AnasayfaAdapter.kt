package com.example.printup.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.printup.databinding.AnasayfaRowBinding
import com.example.printup.tablo.*
import com.squareup.picasso.Picasso

class AnasayfaAdapter(val anasayfaList:ArrayList<AnasayfaBanner>): RecyclerView.Adapter<AnasayfaAdapter.AnasayfaHolder>() {



    inner class AnasayfaHolder(val binding: AnasayfaRowBinding):RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnasayfaHolder {

        val binding=AnasayfaRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AnasayfaHolder(binding)
    }

    override fun onBindViewHolder(holder: AnasayfaHolder, position: Int) {
       holder.itemView.setOnClickListener {
           if(position==1){
               val intent=Intent(holder.itemView.context, UnluTablolarActivity::class.java)
               holder.itemView.context.startActivity(intent)
           }
           else if (position==2){
               val intent=Intent(holder.itemView.context, ManzaraTabloActivity::class.java)
               holder.itemView.context.startActivity(intent)
           }
           else if(position==3){
               val intent=Intent(holder.itemView.context, SoyutTablolarActivity::class.java)
               holder.itemView.context.startActivity(intent)
           }
           else if (position==4){
               val intent=Intent(holder.itemView.context, ModernTablolarActivity::class.java)
               holder.itemView.context.startActivity(intent)
           }
           else{
               val intent=Intent(holder.itemView.context, OzelTabloActivity::class.java)
               holder.itemView.context.startActivity(intent)
           }



       }
        Picasso.get().load(anasayfaList.get(position).imgurl).into(holder.binding.bannerImg)
    }

    override fun getItemCount(): Int {
        return anasayfaList.size
    }

}