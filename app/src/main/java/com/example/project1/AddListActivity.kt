package com.example.project1

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.project1.databinding.ActivityAddListBinding
import com.example.project1.fragments.HomeFragment
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage

class AddListActivity : AppCompatActivity() { //새로운 상품 등록.
    private lateinit var storage: FirebaseStorage
    private val db: FirebaseFirestore = Firebase.firestore
    lateinit var binding : ActivityAddListBinding
    private val itemsCollectionRef = db.collection("Item") // items는 Collection ID
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        storage = Firebase.storage

        val spinner = binding.spinnerStatus.selectedItem.toString()

            //텍스트뷰에 추가할 상품의 정보 입력후 상품 추가 버튼 클릭시 해당 정보들이 DB에 추가후 메인액티비티로 이동. 완료
        binding.addButton.setOnClickListener {

            val title = binding.titleEditText.text.toString()
            val price = binding.priceEditText.text.toString()
            val content = binding.ContentEditText.text.toString()

            val Item = hashMapOf(
                "itemtitle" to title, //상품이름
                "itemprice" to price, //상품가격
                "itemcontent" to content, //상품내용
                "ID" to auth.currentUser?.email.toString(), //작성자 이메일.
                "status" to spinner, //판매여부
                "uid" to auth.currentUser?.uid.toString()

            )
            itemsCollectionRef.add(Item)
                .addOnSuccessListener {
                    Log.d("AddItem","Item Add on FireStore Success")
                }
                .addOnFailureListener {
                    Log.d("AddItem","Item Add on FireStore Fail")
                }
            Log.d("add",title)


            val intent = Intent(this,MainActivity::class.java)

            startActivity(intent)
        }


            //상품 추가 페이지의 백버튼 클릭시 메인 액티비티로 이동.
        binding.backButton.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}