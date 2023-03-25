package com.example.project_applepie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Personal : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal)

//      이전 화면 이동
        findViewById<Button>(R.id.back).setOnClickListener {
            finish()
        }
    }
}