package com.example.printup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.models.SlideModel
import com.example.printup.adapter.AnasayfaAdapter
import com.example.printup.adapter.AnasayfaBanner
import com.example.printup.databinding.ActivityAnaSayfaBinding
import com.example.printup.odeme.AlisVerisSepetiActivity
import com.example.printup.profil.ProfilActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class AnaSayfaActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private var db = Firebase.firestore
    private lateinit var storage: FirebaseStorage
    private lateinit var docid: String
    private lateinit var binding: ActivityAnaSayfaBinding
    private lateinit var anasayfaBannerArrayList: ArrayList<AnasayfaBanner>
    private lateinit var anasayfaAdapter: AnasayfaAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnaSayfaBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        db = Firebase.firestore
        auth = Firebase.auth
        storage = Firebase.storage

        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(  "https://www.cercevelet.com/uploads/images/201806/1-1.jpg" ))
        imageList.add(SlideModel("https://cdn.vatanbilgisayar.com/UPLOAD/GENERAL/landing-page/10avaran/banner.png?v=v5456"))
        imageList.add(SlideModel("https://www.egonomik.com/wp-content/uploads/2018/04/kampanya-firsat-indirim.jpg"))
        val imageSlider = binding.imageSlider
        imageSlider.setImageList(imageList)


        anasayfaBannerArrayList= ArrayList<AnasayfaBanner>()
        verileriAl()
        binding.anaSayfaRecy.layoutManager= LinearLayoutManager(this)
        anasayfaAdapter= AnasayfaAdapter(anasayfaBannerArrayList)
        binding.anaSayfaRecy.adapter=anasayfaAdapter


    }





    fun verileriAl() {
         db.collection("banner").addSnapshotListener { value, error ->

            if (error != null) {
                Toast.makeText(this, error.localizedMessage, Toast.LENGTH_LONG).show()
            } else {
                if (value != null) {
                    for (document in value) {
                        val imgurl = document.get("url") as String

                        val banner = AnasayfaBanner(imgurl)
                        anasayfaBannerArrayList.add(banner)


                    }
                    anasayfaAdapter.notifyDataSetChanged()
                }


            }


        }


    }

    fun sepeteGit(view: View){
        val intent=Intent(this@AnaSayfaActivity, AlisVerisSepetiActivity::class.java)
        startActivity(intent)
    }
    fun profileGir(view: View) {
        val intent = Intent(this@AnaSayfaActivity, ProfilActivity::class.java)
        startActivity(intent)

    }

    fun anasayfa(view: View){
        val intent=Intent(this@AnaSayfaActivity,AnaSayfaActivity::class.java)
        startActivity(intent)
        finish()
    }



}

