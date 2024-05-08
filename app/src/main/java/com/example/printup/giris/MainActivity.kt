package com.example.printup.giris

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.printup.AnaSayfaActivity
import com.example.printup.databinding.ActivityMainBinding
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
    private lateinit var analytics: FirebaseAnalytics
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db:FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)





        db=Firebase.firestore
        auth = Firebase.auth


        val currentuser=auth.currentUser

        if(currentuser!=null){
            val intent= Intent(this@MainActivity, AnaSayfaActivity::class.java)
            startActivity(intent)
            finish()
        }



    }

    fun girisYap(view: View){
        var email=binding.kullaniciText.text.toString()
        var password=binding.parolaText.text.toString()

        if (email.isNotEmpty()||password.isNotEmpty()){
            auth.signInWithEmailAndPassword(email,password).addOnSuccessListener(){
                val intent= Intent(this@MainActivity, AnaSayfaActivity::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener { Toast.makeText(this, it.localizedMessage, Toast.LENGTH_SHORT).show() }

        }
        else{
            Toast.makeText(this, "Lütfen şifre ve mail giriniz!", Toast.LENGTH_LONG).show()
        }

    }
    fun yeniKullanici(view: View){
        val intent= Intent(this@MainActivity, YeniKullaniciActivity::class.java)
        startActivity(intent)





    }

}