package com.example.printup.odeme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.printup.AnaSayfaActivity
import com.example.printup.databinding.ActivityOdemeOnayBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.properties.Delegates
import kotlin.random.Random

class OdemeOnayActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOdemeOnayBinding
    private var no by Delegates.notNull<Int>()
    private lateinit var auth: FirebaseAuth
    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOdemeOnayBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        db= Firebase.firestore
        auth = Firebase.auth

        val r= Random(999999)
        no = (100000..999999).shuffled().last()
        binding.siparisNoText.text=no.toString()
        verileriEkle()

    }

    fun anasayfa(view: View){
        val intent=Intent(this@OdemeOnayActivity, AnaSayfaActivity::class.java)
        startActivity(intent)
        finish()
    }
    fun verileriEkle() {
        val siparisMap= hashMapOf<String,Any>()
        siparisMap.put("email",auth.currentUser!!.email.toString())
        siparisMap.put( "no",no)
        db.collection("siparislistesi").add(siparisMap)
    }
}