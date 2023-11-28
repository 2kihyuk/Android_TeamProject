package com.example.project1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.project1.databinding.ActivityAddListBinding
import com.example.project1.databinding.ActivityChatBinding
import com.example.project1.databinding.ActivityListItemNoBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class ChatActivity : AppCompatActivity() {
    lateinit var binding: ActivityChatBinding
    private val db: FirebaseFirestore = Firebase.firestore
    private lateinit var auth: FirebaseAuth
    private val MessageCollectionRef = db.collection("Message")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        auth = FirebaseAuth.getInstance()
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //DB에    -  메시지 보낸 사람 uid = senderId  , 메시지 받을 사람 uid =receiverId , 메시지 내용 -content로 저장 성공.
        binding.SendBtn.setOnClickListener {
//            binding.ChatText.text의 내용을 db에 저장.

            if(binding.ChatText.text.isEmpty()){
                Toast.makeText(this,"보낼 메시지를 입력하세요.",Toast.LENGTH_SHORT).show()
            }
            val chattext = binding.ChatText.text.toString()
            val receiveruid = intent.getStringExtra("receiverId") //해당 글 작성자의 uid
            val uid = intent.getStringExtra("sellerName")
            val what = intent.getStringExtra("what")
            val MessageSenderEmail = intent.getStringExtra("MessageSenderEmail") //메시지 보내는 사람 이메일.


            val Message = hashMapOf(
                "content" to chattext,
                "receiverId" to receiveruid,
                "senderId" to uid,
                "what" to  what,
                "MessageSenderEmail" to MessageSenderEmail //메시지 보내는 사람 이메일.
            )
            MessageCollectionRef.add(Message).addOnSuccessListener {
                Toast.makeText(this,"메시지 전송 성공",Toast.LENGTH_SHORT).show()
                Log.d("Send","메시지 저장 성공")
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)

            }.addOnFailureListener {
                Toast.makeText(this,"메시지 전송 실패",Toast.LENGTH_SHORT).show()
                Log.d("Send","메시지 저장 실패")
            }



            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }











    }
}