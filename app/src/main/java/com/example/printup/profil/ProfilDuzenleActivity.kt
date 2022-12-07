package com.example.printup.profil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.printup.databinding.ActivityProfilDuzenleBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class ProfilDuzenleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfilDuzenleBinding
    private lateinit var auth: FirebaseAuth
    private var db = Firebase.firestore
    private lateinit var storage: FirebaseStorage
    private lateinit var docid: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfilDuzenleBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        db=Firebase.firestore
        auth = Firebase.auth
        storage=Firebase.storage



        verileriAl()
    }





    fun verileriAl() {
        db.collection("kullaniciBilgileri").whereEqualTo("email", auth.currentUser!!.email).addSnapshotListener { value, error ->

            if (error == null) {
                if (value != null) {
                    for (document in value) {
                        val ad = document.getString("ad")
                        val email = document.getString("email")
                        val soyad = document.getString("soyad")

                    docid =document.id


                        binding.adText.setText("$ad")
                        binding.soyadText.setText("$soyad")
                        binding.mailAdresText.setText("$email")

                    }
                }
            }
        }



    }

        fun geriTusu(view: View){
        val intent= Intent(this@ProfilDuzenleActivity, ProfilActivity::class.java)
        startActivity(intent)
            finish()

    }

    fun guncelle(view: View){

            val guncelle = db.collection("kullaniciBilgileri").document(docid)



        val alert = AlertDialog.Builder(this@ProfilDuzenleActivity)

        alert.setTitle("Güncelleme")
        alert.setMessage("Bilgilerin doğruluğunu onaylıyor musunuz?")
        alert.setPositiveButton("Evet") {dialog, which ->

            val sifre=binding.sifreText.text.toString()
            val yenidensifre=binding.sifreYeniden1Text.text.toString()
            if (sifre.isNotEmpty()&&yenidensifre.isNotEmpty()&&sifre.equals(yenidensifre)){
                val yenisifre=binding.sifreText.text.toString()
                guncelle.update("sifre",yenisifre)
                auth.currentUser!!.updatePassword(yenisifre)
                Toast.makeText(this, "Profil bilgileri güncellendi!", Toast.LENGTH_LONG).show()


            }
            else if (!sifre.equals(yenidensifre)){
                Toast.makeText(this,"Şifreler uyuşmuyor", Toast.LENGTH_SHORT).show()

            }
            else{
                val yeniad=binding.adText.text.toString()
                val yenisoyad=binding.soyadText.text.toString()

                guncelle.update("ad",yeniad)
                guncelle.update("soyad",yenisoyad)


                Toast.makeText(this, "Ad ve Soyad bilgileri güncellendi!", Toast.LENGTH_LONG).show()

            }

            verileriAl()

        }

        alert.setNegativeButton("Hayır") {dialog, which ->
                 }

        alert.show()


        }

    fun mailToast(view: View){

        Toast.makeText(this, "Kayıtlı mail adresi değiştirilemez!", Toast.LENGTH_LONG).show()

    }

}

