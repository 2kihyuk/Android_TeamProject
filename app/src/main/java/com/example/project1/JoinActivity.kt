package com.example.project1

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.project1.databinding.ActivityJoinBinding
import com.example.project1.databinding.ActivityLoginBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.firestore

class JoinActivity : AppCompatActivity() { //회원 가입기능.

    lateinit var binding: ActivityJoinBinding
    private lateinit var auth: FirebaseAuth
    private var db: FirebaseFirestore = Firebase.firestore
    private var userRef = db.collection("user")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJoinBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //binding.

        auth = Firebase.auth //authentication
        val db = Firebase.firestore //firestore

        val joinbtn = binding.joinBtn //회원 가입 버튼


        joinbtn.setOnClickListener {

            var isGotoJoin = true

            val id = binding.Id.text.toString()
            val pwd = binding.Password.text.toString()
            val name = binding.Name.text.toString()
            val birthday=binding.BirthDay.text.toString()  //db에 이 정보 저장해야하나?

            if(id.isEmpty()){
                Toast.makeText(this,"이메일이 비어있습니다.",Toast.LENGTH_SHORT).show()
                 isGotoJoin =false
            }
            if(pwd.isEmpty()){
                Toast.makeText(this,"비밀번호가 비어있습니다.",Toast.LENGTH_SHORT).show()
                isGotoJoin =false
            }
            if(pwd.length<6){
                Toast.makeText(this,"비밀번호는 6자리 이상 입력해주세요.",Toast.LENGTH_SHORT).show()
                isGotoJoin =false
            }

            if(isGotoJoin){

                auth.createUserWithEmailAndPassword(id, pwd) // 회원 가입 .
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user = Firebase.auth.currentUser

                        Toast.makeText(this,"회원가입 성공.",Toast.LENGTH_SHORT).show()
                        saveUser(user?.uid,name,birthday)

                        val intent = Intent(this,MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)
                        finish()

                    }
                    else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(this, "회원가입 실패.", Toast.LENGTH_SHORT,).show()
                    }

                }

             }

        }


     }
    private fun saveUser(userId : String?,name:String?,birthday:String?){
        val UserMap = hashMapOf(
            "name" to name,
            "birthday" to birthday,
            "uid" to auth.currentUser?.uid
        )

        userRef.document(userId ?:"")
            .set(UserMap)
            .addOnSuccessListener {  }
            .addOnFailureListener {  }

    }


    }



