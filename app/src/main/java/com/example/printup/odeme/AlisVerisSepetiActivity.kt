package com.example.printup.odeme

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isNotEmpty
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.printup.AnaSayfaActivity
import com.example.printup.adapter.AlisVerisSepetiAdapter
import com.example.printup.adapter.Sepet
import com.example.printup.databinding.ActivityAlisVerisSepetiBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class AlisVerisSepetiActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private var db = Firebase.firestore
    private lateinit var storage: FirebaseStorage
    private lateinit var docid: String
    private lateinit var binding: ActivityAlisVerisSepetiBinding
    private lateinit var alisVerisSepetiAdapter: AlisVerisSepetiAdapter
    private lateinit var alisVerisList: ArrayList<Sepet>

    private var toplamTutar =0
    private lateinit var adet:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlisVerisSepetiBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        db = Firebase.firestore
        auth = Firebase.auth
        storage = Firebase.storage

        alisVerisList= ArrayList<Sepet>()
        verileriAl()



        binding.alisVerisRecy.layoutManager= LinearLayoutManager(this)
        alisVerisSepetiAdapter= AlisVerisSepetiAdapter(alisVerisList)
        binding.alisVerisRecy.adapter=alisVerisSepetiAdapter


    }



    fun verileriAl() {
        db.collection("sepet").whereEqualTo("email",auth.currentUser!!.email).addSnapshotListener { value, error ->

            if (error != null) {
                Toast.makeText(this, error.localizedMessage, Toast.LENGTH_LONG).show()
            } else {
                if (value != null) {
                    for (document in value) {
                        val tabloAd = document.get("tabload") as String
                        val cerceve = document.get("cerceve") as String
                        val fiyat = document.get("fiyat") as String
                        val boyut = document.get("boyut") as String

                        val sepet = Sepet(tabloAd,boyut,fiyat,cerceve)
                        alisVerisList.add(sepet)
                        toplamTutar=(fiyat.toInt())+toplamTutar


                    }
                    binding.toplamTutar.setText("$toplamTutar TL")

                    alisVerisSepetiAdapter.notifyDataSetChanged()
                }


            }


        }
    }

    fun sepetiTemizle(view: View){
        db.collection("sepet").whereEqualTo("email",auth.currentUser!!.email).addSnapshotListener { value, error ->

            if (error != null) {
                Toast.makeText(this, error.localizedMessage, Toast.LENGTH_LONG).show()
            } else {
                if (value != null) {
                    for (document in value) {
                        docid=document.id
                       db.collection("sepet").document(docid).delete()


                    }
                    alisVerisSepetiAdapter.notifyDataSetChanged()
                }


            }


        }
        val intent=Intent(this@AlisVerisSepetiActivity, AnaSayfaActivity::class.java)
        startActivity(intent)
        Toast.makeText(this, "Sepet Temizlendi", Toast.LENGTH_SHORT).show()
    }


    fun devamEt(view: View){
        if (binding.alisVerisRecy.isNotEmpty()){
            val intent=Intent(this@AlisVerisSepetiActivity,OdemeSayfasiActivity::class.java)
            startActivity(intent)
        }else{
            Toast.makeText(this, "Lütfen sepete ürün ekleyin!", Toast.LENGTH_SHORT).show()
        }
    }
}