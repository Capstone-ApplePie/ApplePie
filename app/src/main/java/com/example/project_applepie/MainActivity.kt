package com.example.project_applepie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//      로그인 버튼 클릭 시 로그인 화면으로 이동
        findViewById<Button>(R.id.signIn).setOnClickListener{
            val intent = Intent(this, SignIn::class.java)
            startActivity(intent)
        }

//      회원가입 버튼 클릭 시 회원가입 화면으로 이동
        findViewById<Button>(R.id.signUp).setOnClickListener{
            val intent = Intent(this, CompanyCheck::class.java)
            startActivity(intent)
        }
    }
}