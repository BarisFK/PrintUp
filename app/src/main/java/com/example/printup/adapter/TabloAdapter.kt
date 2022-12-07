package com.example.printup.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.printup.databinding.TablolarRowBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

class TabloAdapter(val tabloList:ArrayList<Tablo>):RecyclerView.Adapter<TabloAdapter.TabloHolder>() {
    class TabloHolder(val binding: TablolarRowBinding):RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TabloHolder {
        val binding=TablolarRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TabloHolder(binding)
    }

    override fun onBindViewHolder(holder: TabloHolder, position: Int) {
        holder.binding.tabloAd.text=tabloList.get(position).tabloAd
        holder.binding.fiyatText.text=tabloList.get(position).fiyat
        holder.binding.sanatciAd.text=tabloList.get(position).sanatciad
        holder.binding.tabloBoyutText.text=tabloList.get(position).boyut
        Picasso.get().load(tabloList.get(position).tabloUrl).into(holder.binding.tabloImg)

        holder.binding.sepeteEkle.setOnClickListener {
            val sepetMap= hashMapOf<String,Any>()
            sepetMap.put("email",Firebase.auth.currentUser!!.email.toString())
            sepetMap.put("cerceve","Klasik Çerçeve")
            sepetMap.put("boyut",holder.binding.tabloBoyutText.text.toString())
            sepetMap.put("fiyat",holder.binding.fiyatText.text.toString())
            sepetMap.put("tabload",holder.binding.tabloAd.text.toString())
           FirebaseFirestore.getInstance().collection   ("sepet").add(sepetMap)


        }
    }

    override fun getItemCount(): Int {
       return tabloList.size
    }

}