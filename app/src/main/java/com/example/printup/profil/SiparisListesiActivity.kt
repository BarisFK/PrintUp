package com.example.printup.profil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.printup.R
import com.example.printup.adapter.Siparis
import com.example.printup.adapter.SiparisAdapter
import com.example.printup.adapter.Tablo
import com.example.printup.adapter.TabloAdapter
import com.example.printup.databinding.ActivityAdresDuzenleBinding
import com.example.printup.databinding.ActivitySiparisListesiBinding
import com.example.printup.databinding.SiparisRowBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SiparisListesiActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySiparisListesiBinding
    private lateinit var auth: FirebaseAuth
    private var db = Firebase.firestore
    private lateinit var siparisList: ArrayList<Siparis>
    private lateinit var siparisAdapter: SiparisAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySiparisListesiBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        db=Firebase.firestore
        auth = Firebase.auth
        siparisList= ArrayList<Siparis>()
        verileriAl()
        binding.siparisRecy.layoutManager= LinearLayoutManager(this)
        siparisAdapter= SiparisAdapter(siparisList)
        binding.siparisRecy.adapter=siparisAdapter

    }

    fun verileriAl(){
        db.collection("siparislistesi").whereEqualTo( "email",auth.currentUser!!.email).addSnapshotListener { value, error ->

            if (error != null) {
                Toast.makeText(this, error.localizedMessage, Toast.LENGTH_LONG).show()
            } else {
                if (value != null) {
                    for (document in value) {
                        val no = document.get("no") as Long
                        val siparis = Siparis(no)
                        siparisList.add(siparis)
                    }

                    siparisAdapter.notifyDataSetChanged()
                }


            }


        }
    }

    fun geritusu(view: View){
        val intent=Intent(this@SiparisListesiActivity,ProfilActivity::class.java)
        startActivity(intent)
        finish()
    }

}