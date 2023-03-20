package com.example.project_applepie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 팀원 모집 화면 이동 처리
        findViewById<Button>(R.id.Recruitment).setOnClickListener {
            val intent = Intent(this, Recruit::class.java)
            startActivity(intent)
        }

        // 외주 화면 이동 처리
        findViewById<Button>(R.id.Outsourcing).setOnClickListener {
            val intent = Intent(this, Outsourcing::class.java)
            startActivity(intent)
        }

        // 과외 화면 이동 처리
        findViewById<Button>(R.id.Lesson).setOnClickListener {
            val intent = Intent(this, Lesson::class.java)
            startActivity(intent)
        }

        // 글 작성 이동 처리
        findViewById<Button>(R.id.writing).setOnClickListener {
            val intent = Intent(this, Writing::class.java)
            startActivity(intent)
        }
    }
}