package com.example.printup.giris

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.printup.AnaSayfaActivity
import com.example.printup.databinding.ActivityYeniKullaniciBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class YeniKullaniciActivity : AppCompatActivity() {

    private lateinit var binding:ActivityYeniKullaniciBinding
    private lateinit var auth: FirebaseAuth
    private var db = Firebase.firestore
    private lateinit var storage: FirebaseStorage
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityYeniKullaniciBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        db=Firebase.firestore
        auth = Firebase.auth
        storage=Firebase.storage
    }

    fun uyeOl(view: View){
        var email=binding.kullaniciMailText.text.toString()
        var sifre=binding.kullaniciSifreText.text.toString()
        var sifreYeniden=binding.sifreYenidenText.text.toString()
        var ad=binding.kullaniciAdText.text.toString()
        var soyad=binding.kullaniciSoyadText.text.toString()

        if (email.isNotEmpty()&&sifre.isNotEmpty()&&sifreYeniden.isNotEmpty()){
            if(sifre==sifreYeniden){
                auth.createUserWithEmailAndPassword(email,sifre).addOnSuccessListener {
                    val kullaniciMap= hashMapOf<String,Any>()
                    kullaniciMap.put("email",email)
                    kullaniciMap.put("ad",ad)
                    kullaniciMap.put("soyad",soyad)
                    kullaniciMap.put("sifre",sifre)

                    val adresMap= hashMapOf<String,Any>()
                    adresMap.put("email",email)
                    adresMap.put("sehir","")
                    adresMap.put("ilce","")
                    adresMap.put("mahalle","")
                    adresMap.put("cepno","")
                    adresMap.put("postakodu","")
                    adresMap.put("acikadres","")
                    db.collection("kullaniciAdresBilgileri").add(adresMap)


                    db.collection("kullaniciBilgileri").add(kullaniciMap).addOnSuccessListener {
                    }.addOnFailureListener {
                        Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
                    }
                    
                    val intent= Intent(this@YeniKullaniciActivity, AnaSayfaActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)


                    Toast.makeText(this, "Hesap oluşturuldu!", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener { Toast.makeText(this, it.localizedMessage, Toast.LENGTH_SHORT).show() }
            }
            else{
                Toast.makeText(this, "Şifreler uyuşmuyor!", Toast.LENGTH_LONG).show()
            }


        }
        else{
            Toast.makeText(this, "Lütfem mail ve şifre giriniz!", Toast.LENGTH_LONG).show()
        }
        

       



    }

    fun geriTusu(view: View){
        val intent= Intent(this@YeniKullaniciActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}