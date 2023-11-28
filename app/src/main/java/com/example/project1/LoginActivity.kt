package com.example.project1

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.project1.databinding.ActivityLoginBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        val loginbtn = binding.LoginBtn
        val signupbtn = binding.SignupBtn
        val db = Firebase.firestore


        signupbtn.setOnClickListener {  //회원가입 버튼 클릭시.
            startActivity(Intent(this, JoinActivity::class.java))
            finish()
        }


        loginbtn.setOnClickListener {  //로그인 기능.
            val idtext =  binding.IdText.text.toString()
            val pwdtext = binding.Passwordtext.text.toString()

            auth.signInWithEmailAndPassword(idtext, pwdtext)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) { //로그인 성공

                        val intent = Intent(this,MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)
                        Toast.makeText(this,"로그인 성공", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        Toast.makeText(this,"로그인 실패",Toast.LENGTH_SHORT).show()

                    }
                }
        }





    }


}