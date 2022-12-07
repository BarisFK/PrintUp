package com.example.printup.profil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.printup.MapsActivity
import com.example.printup.databinding.ActivityAdresDuzenleBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class AdresDuzenleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdresDuzenleBinding
    private lateinit var auth: FirebaseAuth
    private var db = Firebase.firestore
    private lateinit var storage: FirebaseStorage
    private lateinit var docid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdresDuzenleBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        db=Firebase.firestore
        auth = Firebase.auth
        storage=Firebase.storage
        verileriAl()

    }

    fun geriTusu(view: View){
        val intent= Intent(this@AdresDuzenleActivity, ProfilActivity::class.java)
        startActivity(intent)
        finish()

    }
    fun konumgoster(view: View){
        val intent=Intent(this@AdresDuzenleActivity, MapsActivity::class.java)
        startActivity(intent)
    }


    fun verileriAl() {
        db.collection("kullaniciAdresBilgileri").whereEqualTo("email", auth.currentUser!!.email).addSnapshotListener { value, error ->

            if (error == null) {
                if (value != null) {
                    for (document in value) {
                        val sehir = document.getString("sehir")
                        val ilce = document.getString("ilce")
                        val mahalle = document.getString("mahalle")
                        val postakodu=document.getString("postakodu")
                        val cepno=document.getString("cepno")
                        val acikadres=document.getString("acikadres")

                        docid =document.id

                        binding.sehirText.setText(sehir)
                        binding.ilceText.setText(ilce)
                        binding.mahalleText.setText(mahalle)
                        binding.postaText.setText(postakodu)
                        binding.cepnoText.setText(cepno)
                        binding.acikadresText.setText(acikadres)


                    }
                }
            }
        }



    }
    fun guncelle(view: View){
        val guncelle = db.collection("kullaniciAdresBilgileri").document(docid)

        val sehir=binding.sehirText.text.toString()
        val ilce=binding.ilceText.text.toString()
        val mahalle=binding.mahalleText.text.toString()
        val postakodu=binding.postaText.text.toString()
        val cepno=binding.cepnoText.text.toString()
        val acikadres=binding.acikadresText.text.toString()

        if(sehir.isNotEmpty()&&ilce.isNotEmpty()&&mahalle.isNotEmpty()&&postakodu.isNotEmpty()&&cepno.isNotEmpty()&&acikadres.isNotEmpty()){
            guncelle.update("sehir",sehir)
            guncelle.update("ilce",ilce)
            guncelle.update("mahalle",mahalle)
            guncelle.update("cepno",cepno)
            guncelle.update("acikadres",acikadres)
            guncelle.update("postakodu",postakodu)

            Toast.makeText(this, "Adres bilgileri güncellendi!", Toast.LENGTH_LONG).show()

        }
        else{
            Toast.makeText(this, "Lütfen boşlukları doldurunuz!", Toast.LENGTH_LONG).show()

        }

    }

}