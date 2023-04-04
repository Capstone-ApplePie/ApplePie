package com.example.project_applepie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.project_applepie.databinding.ActivityRecruitTeamBinding
import com.example.project_applepie.model.recuit

class RecruitTeamActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rtBinding = ActivityRecruitTeamBinding.inflate(layoutInflater)
        setContentView(rtBinding.root)

        val intent = intent
        val recruitData = intent.getSerializableExtra("data") as recuit?

        rtBinding.sample.text = recruitData?.title;
        rtBinding.sampleDetail.text = recruitData?.detail;
    }
}