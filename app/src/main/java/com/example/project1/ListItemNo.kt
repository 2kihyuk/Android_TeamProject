package com.example.project1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.project1.databinding.ActivityListItemBinding
import com.example.project1.databinding.ActivityListItemNoBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class ListItemNo : AppCompatActivity() {

    lateinit var binding: ActivityListItemNoBinding
    private val db: FirebaseFirestore = Firebase.firestore
    private lateinit var auth: FirebaseAuth
    private val itemsCollectionRef = db.collection("Item") // items는 Collection ID
//    private val usersCollectionRef = db.collection("user").document(auth.currentUser?.uid.toString())
    private lateinit var usersCollectionRef: DocumentReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListItemNoBinding.inflate(layoutInflater)

        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        usersCollectionRef = db.collection("user").document(auth.currentUser?.uid.toString())


        val docid =intent.getStringExtra("DocId")


        Log.d("DocumentCheck",docid.toString())

        val title = intent.getStringExtra("listTitle")
        val content = intent.getStringExtra("listContents")
        val price = intent.getStringExtra("listPrice")
        val sellerid = intent.getStringExtra("ID")
        val uid = intent.getStringExtra("uid")



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

        //채팅 버튼 클릭시, 판매자에게 채팅 보낼 액티비티인 ChatActivity로 이동.
        binding.Chatbutton.setOnClickListener {
            val intent = Intent(this,ChatActivity::class.java)
            intent.putExtra("receiverId",uid)
            intent.putExtra("sellerName",sellerid)
            intent.putExtra("what",title)
            //여기서 판매자의 정보를 보내야함. 판매자의 uid를 보내서.chatactivity에서 받아서,
            intent.putExtra("MessageSenderEmail",auth.currentUser?.email.toString())

            Log.d("checkId",auth.currentUser?.email.toString())
            startActivity(intent)
            finish()
        }


        //백 버튼 누르기
        binding.backButton.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }



    }


}