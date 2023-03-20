package com.example.project_applepie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Recruit : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recruit)

        // 상단 이전 버튼
        findViewById<Button>(R.id.back).setOnClickListener {
            finish()
        }
    }
}