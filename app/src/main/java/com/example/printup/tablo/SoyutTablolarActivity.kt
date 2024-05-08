package com.example.printup.tablo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.printup.adapter.Tablo
import com.example.printup.adapter.TabloAdapter
import com.example.printup.databinding.ActivitySoyutTablolarBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class SoyutTablolarActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private var db = Firebase.firestore
    private lateinit var storage: FirebaseStorage
    private lateinit var docid: String
    private lateinit var binding:ActivitySoyutTablolarBinding
    private lateinit var tabloArrayList: ArrayList<Tablo>
    private lateinit var tabloAdapter: TabloAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivitySoyutTablolarBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        db = Firebase.firestore
        auth = Firebase.auth
        storage = Firebase.storage

        tabloArrayList= ArrayList<Tablo>()
        verileriAl()
        binding.tablolarRecy.layoutManager= LinearLayoutManager(this)
        tabloAdapter= TabloAdapter(tabloArrayList)
        binding.tablolarRecy.adapter=tabloAdapter

    }

    fun verileriAl() {
        db.collection("soyuttablolar").addSnapshotListener { value, error ->

            if (error != null) {
                Toast.makeText(this, error.localizedMessage, Toast.LENGTH_LONG).show()
            } else {
                if (value != null) {
                    for (document in value) {
                        val tabloAd = document.get("ad") as String
                        val sanatciAd = document.get("sanatciAd") as String
                        val fiyat = document.get("fiyat") as String
                        val tablourl = document.get("url") as String
                        val boyut = document.get("boyut") as String

                        val tablo = Tablo(tabloAd, sanatciAd, fiyat, tablourl, boyut)
                        tabloArrayList.add(tablo)


                    }
                    tabloAdapter.notifyDataSetChanged()
                }


            }


        }
    }
}