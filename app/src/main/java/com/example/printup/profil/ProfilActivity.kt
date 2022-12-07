package com.example.printup.profil

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.printup.AnaSayfaActivity
import com.example.printup.databinding.ActivityProfilBinding
import com.example.printup.giris.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage


class ProfilActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfilBinding
    private lateinit var auth: FirebaseAuth
    private var db = Firebase.firestore
    private lateinit var storage: FirebaseStorage
    private lateinit var docid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfilBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        db=Firebase.firestore
        auth = Firebase.auth
        storage=Firebase.storage

        db.collection("kullaniciBilgileri").whereEqualTo("email",auth.currentUser!!.email).addSnapshotListener{value,error ->
            if (value != null) {
                for (document in value){
                    val ad=document.getString("ad")
                    val soyad=document.getString("soyad")
                    binding.kullaniciAdiText.setText("$ad $soyad")
                }

            }
        }
    }

    fun profilDuzenle(view: View){
        val intent= Intent(this@ProfilActivity, ProfilDuzenleActivity::class.java)
        startActivity(intent)

    }
    fun adresDuzenle(view: View){
        val intent= Intent(this@ProfilActivity, AdresDuzenleActivity::class.java)
        startActivity(intent)

    }
    fun siparisListesi(view: View){
        val intent=Intent(this@ProfilActivity,SiparisListesiActivity::class.java)
        startActivity(intent)
    }
    fun cikisYap(view: View){

        auth.signOut()

        val intent=Intent(this@ProfilActivity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)

    }
    fun geriTusu(view: View){
        val intent= Intent(this@ProfilActivity, AnaSayfaActivity::class.java)
        startActivity(intent)
        finish()

    }

}