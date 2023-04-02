package com.example.project_applepie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class SignIn : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        // 아이디 찾기 추가...
        /*
        findViewById<EditText>(R.id.findId).setOnClickListener {

        }*/

        findViewById<Button>(R.id.logInUser).setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}