package com.example.project_applepie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout

class Writing : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_writing)

        // 상단 이전 버튼
        findViewById<Button>(R.id.back).setOnClickListener {
            finish()
        }

        // 팀원 모집 글 작성 화면 이동
        findViewById<LinearLayout>(R.id.Writing_Recruit).setOnClickListener{
            val intent = Intent(this, WritingRecruit::class.java)
            startActivity(intent)
        }
    }
}