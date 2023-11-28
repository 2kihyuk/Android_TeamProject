package com.example.project1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.project1.databinding.ActivityListItemBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class ListItem : AppCompatActivity() {

     lateinit var binding: ActivityListItemBinding
    private val db: FirebaseFirestore = Firebase.firestore
    private lateinit var auth: FirebaseAuth
    private val itemsCollectionRef = db.collection("Item") // items는 Collection ID
//    private val usersCollectionRef = db.collection("user").document("user")
private lateinit var usersCollectionRef: DocumentReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        usersCollectionRef = db.collection("user").document(auth.currentUser?.uid.toString())

        val docid =intent.getStringExtra("DocId")

        Log.d("DocumentCheck",docid.toString())

        val title = intent.getStringExtra("listTitle")
        val content = intent.getStringExtra("listContents")
        val price = intent.getStringExtra("listPrice")
        val sellerid = intent.getStringExtra("ID")
//        val priceWithWon = "$price"+"원"


        binding.detailTitle.text=title
        binding.price.text=price
        binding.detailContent.text=content
        binding.SellerNameTv.text = sellerid


//        usersCollectionRef.get().addOnSuccessListener {
//                document ->
//            if (document.exists()) {
//                val idValue = document.getString("ID")
//                binding.SellerNameTv.text=idValue
//                Log.d("Firestore", "ID value: $idValue")
//            } else {
//                Log.d("Firestore", "No such document")
//            }
//        }.addOnFailureListener { exception ->
//            Log.d("Firestore", "get failed with ", exception)
//
//
//        }



        binding.backButton.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        //상품 수정 버튼 클릭시, EditProductActivity를 띄우면서, 지금 가지고 있는 정보들을 보내서, EditProductActivity에서는 인텐트 정보를 받아서 띄워놓고 시작.
        binding.Editbutton.setOnClickListener {
            val intent = Intent(this,EditProductActivity::class.java)
            intent.putExtra("EditTitle",binding.detailTitle.text.toString())
            intent.putExtra("EditPrice",binding.price.text.toString())
            intent.putExtra("EditContent",binding.detailContent.text.toString())
            intent.putExtra("DocId",docid)
            if (docid != null) {
                Log.d("DocId",docid)
            }

            startActivity(intent)

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            val updatedTitle = data?.getStringExtra("title")
            val updatedContent = data?.getStringExtra("content")
            val updatedPrice = data?.getStringExtra("price")

            // Update your UI with the new data
            binding.detailTitle.text = updatedTitle
            binding.detailContent.text = updatedContent
            binding.price.text = updatedPrice
        }
    }



}