package com.example.printup.tablo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.printup.AnaSayfaActivity
import com.example.printup.R
import com.example.printup.databinding.ActivityOzelTabloBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class OzelTabloActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private var db = Firebase.firestore
    private lateinit var storage: FirebaseStorage
    private lateinit var docid: String
    private lateinit var binding: ActivityOzelTabloBinding

    private lateinit var secilengorsel:ImageView
    private lateinit var cerceve:String
    private lateinit var boyut:String
    private lateinit var fiyat:String




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOzelTabloBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        db = Firebase.firestore
        auth = Firebase.auth
        storage = Firebase.storage

        secilengorsel=binding.gorsel


        checkBoxTiklama()


    }

    fun gorselYukle(view: View){
    val intent=Intent()
        intent.action=Intent.ACTION_GET_CONTENT
        intent.type="image/*"
        startActivityForResult(intent,1)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==1){
            secilengorsel.setImageURI(data?.data)
        }
    }
    
    fun beyazcerceve(view: View){
        binding.cerceveRengi.setImageResource(R.drawable.cercevebeyaz)
        cerceve="Beyaz Çerçeve"
    }
    fun siyahcerceve(view: View){
        binding.cerceveRengi.setImageResource(R.drawable.cercevesiyah)
        cerceve="Siyah Çerçeve"
    }
    fun altincerceve(view: View){
        binding.cerceveRengi.setImageResource(R.drawable.cercevealtin)
        cerceve="Altın Çerçeve"    }
    fun gumuscerceve(view: View){
        binding.cerceveRengi.setImageResource(R.drawable.cercevegumus)
        cerceve="Gümüş Çerçeve"
    }
    fun cercevesiz(view: View){
        binding.cerceveRengi.setImageResource(R.drawable.cerceveyok)
        cerceve="Yok"
    }

    fun checkBoxTiklama(){

        binding.checkBox1.setOnCheckedChangeListener { buttonView, isChecked ->
            if (binding.checkBox1.isChecked){
                binding.ozelFiyatText.text="190"
                boyut=binding.kucukBoyut.text.toString()
                fiyat=binding.ozelFiyatText.text.toString()

                binding.checkBox3.isVisible=false
                binding.checkBox2.isVisible=false }
            else{
                binding.ozelFiyatText.text="0"
                binding.checkBox3.isVisible=true
                binding.checkBox2.isVisible=true
            }


        }
        binding.checkBox2.setOnCheckedChangeListener { buttonView, isChecked ->
            if (binding.checkBox2.isChecked){
                binding.ozelFiyatText.text="350"
                boyut=binding.ortaBoyut.text.toString()
                fiyat=binding.ozelFiyatText.text.toString()

                binding.checkBox3.isVisible=false
                binding.checkBox1.isVisible=false }
            else{
                binding.ozelFiyatText.text=" 0"
                binding.checkBox3.isVisible=true
                binding.checkBox1.isVisible=true
            }


        }
        binding.checkBox3.setOnCheckedChangeListener { buttonView, isChecked ->
            if (binding.checkBox3.isChecked){
                binding.ozelFiyatText.text="530"
                boyut=binding.buyukBoyut.text.toString()
                fiyat=binding.ozelFiyatText.text.toString()

                binding.checkBox2.isVisible=false
                binding.checkBox1.isVisible=false }
            else{
                binding.ozelFiyatText.text="0"
                binding.checkBox2.isVisible=true
                binding.checkBox1.isVisible=true
            }


        }

    }

    fun sepeteEkle(view: View){

        if(!binding.checkBox1.isChecked&&!binding.checkBox2.isChecked&&!binding.checkBox3.isChecked){
            Toast.makeText(this, "Lütfen boyut seçiniz!", Toast.LENGTH_SHORT).show()
        }else{
            val intent=Intent(this@OzelTabloActivity,AnaSayfaActivity::class.java)

            val sepetMap= hashMapOf<String,Any>()
            sepetMap.put("email",auth.currentUser!!.email.toString())
            sepetMap.put("cerceve",cerceve)
            sepetMap.put("boyut",boyut)
            sepetMap.put("fiyat",fiyat)
            sepetMap.put("tabload","Özel Tablo")
            db.collection("sepet").add(sepetMap)

            startActivity(intent)

            Toast.makeText(this, "Ürün Sepete Eklendi!", Toast.LENGTH_SHORT).show()

        }

    }



}