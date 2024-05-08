package com.example.printup.odeme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.printup.databinding.ActivityOdemeSayfasiBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class OdemeSayfasiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOdemeSayfasiBinding
    private lateinit var auth: FirebaseAuth
    private var db = Firebase.firestore
    private lateinit var docid:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOdemeSayfasiBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        db = Firebase.firestore
        auth=Firebase.auth


       verileriAl()

    }
    fun verileriAl() {
        db.collection("kullaniciAdresBilgileri").whereEqualTo("email",auth.currentUser!!.email).addSnapshotListener { value, error ->

            if (error != null) {
                Toast.makeText(this, error.localizedMessage, Toast.LENGTH_LONG).show()
            } else {
                if (value != null) {
                    for (document in value) {
                        val sehir=document.getString("sehir") as String
                        val ilce=document.getString("ilce") as String
                        val mahalle=document.getString("mahalle") as String
                        val postakodu=document.getString("postakodu") as String
                        val acikadres=document.getString("acikadres") as String

                        binding.adres1Text.setText("$sehir / $ilce / $mahalle / $postakodu \n$acikadres")

                    }

                }


            }


        }
    }
    fun onayla(view: View){

        if(binding.kartisimText.text.isNotEmpty()&&binding.kartnoText.text.isNotEmpty()&&binding.karttarihiText.text.isNotEmpty()&&binding.cvvText.text.isNotEmpty()&&!binding.adres1Text.text.equals("/ / /")){


            db.collection("sepet").addSnapshotListener { value, error ->

                if (error != null) {
                    Toast.makeText(this, error.localizedMessage, Toast.LENGTH_LONG).show()
                } else {
                    if (value != null) {
                        for (document in value) {
                            docid=document.id
                            db.collection("sepet").document(docid).delete()


                        }
                    }


                }
                val intent=Intent(this@OdemeSayfasiActivity, OdemeOnayActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)

            }
            finish()
        }else{
            Toast.makeText(this, "Lütfen bilgiileri eksiksiz girin!", Toast.LENGTH_SHORT).show()
        }


    }
}