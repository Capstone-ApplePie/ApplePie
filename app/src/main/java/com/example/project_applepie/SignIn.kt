package com.example.project_applepie

import android.content.Intent
import android.os.Build.VERSION_CODES.O
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView

class SignIn : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        // 아이디 찾기 추가...
        /*
        findViewById<EditText>(R.id.findId).setOnClickListener {

        }*/

        // 상단 X 버튼
        findViewById<ImageButton>(R.id.returnMain).setOnClickListener {
            finish()
        }

        // 로그인 버튼
        findViewById<Button>(R.id.logInUser).setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        // 회원가입 문구 클릭 시
        findViewById<TextView>(R.id.goSignUp).setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }
}