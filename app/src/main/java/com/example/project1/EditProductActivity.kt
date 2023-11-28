package com.example.project1

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.project1.databinding.ActivityEditProductBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore


/*
해야 할 것 .

4. 채팅 기능 구현.  => 경미

//채팅 기능만 구현하면 됨
+ 사진 추가도. storage.


평가표상 6,7,8,9,10,11,12 완료
//
 */

class EditProductActivity : AppCompatActivity() {
    private val db: FirebaseFirestore = Firebase.firestore
    private val itemsCollectionRef = db.collection("Item") // items는 Collection ID
    lateinit var binding:ActivityEditProductBinding
    lateinit var selectedItemID: String

    @SuppressLint("SuspiciousIndentation")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProductBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val title = intent.getStringExtra("EditTitle")
        val price = intent.getStringExtra("EditPrice")
        val content = intent.getStringExtra("EditContent").toString()

        selectedItemID = intent.getStringExtra("DocId") ?: ""

        binding.titleEditText.setText(title)
        binding.priceEditText.setText(price)

        binding.ContentEditText.setText(content) //이 부분 잘안됨.



        binding.EditButton.setOnClickListener {
            val updateTitle = binding.titleEditText.text.toString()
            val updatePrice = binding.priceEditText.text.toString()
            val updateContent = binding.ContentEditText.text.toString()
            val updateStatus = binding.spinnerStatus.selectedItem.toString()

            //수정 사항 데이터베이스에 업데이트.
            itemsCollectionRef.document(selectedItemID).update("itemtitle",updateTitle)
            itemsCollectionRef.document(selectedItemID).update("itemprice",updatePrice)
            itemsCollectionRef.document(selectedItemID).update("itemcontent",updateContent)
            itemsCollectionRef.document(selectedItemID).update("status",updateStatus) //판매 상태 변경완료

            val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()

        }


        binding.backButton.setOnClickListener {
            val intent = Intent(this,ListItem::class.java)
            intent.putExtra("title",binding.titleEditText.text.toString())
            intent.putExtra("content",binding.ContentEditText.text.toString())
            intent.putExtra("price",binding.priceEditText.text.toString())
            setResult(RESULT_OK, intent)
            finish()
        }


    }
}