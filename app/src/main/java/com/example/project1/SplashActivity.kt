package com.example.project1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class SplashActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        auth = Firebase.auth

        if(auth.currentUser?.uid==null){
            Handler().postDelayed({
                startActivity(Intent(this,LoginActivity::class.java))
                finish()
            },3000)  //3초뒤에 스플래쉬 꺼주고 메인으로 전환.
        }else{
            Handler().postDelayed({
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            },3000)  //3초뒤에 스플래쉬 꺼주고 메인으로 전환.
        }

    }
}