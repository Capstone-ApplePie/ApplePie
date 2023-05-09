package com.example.project_applepie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import com.example.project_applepie.databinding.ActivityRecruitTeamBinding
import com.example.project_applepie.model.recuit

class RecruitTeamActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rtBinding = ActivityRecruitTeamBinding.inflate(layoutInflater)
        setContentView(rtBinding.root)

        val intent = intent
        val recruitData = intent.getSerializableExtra("data") as recuit?

        rtBinding.ivRtImg.setImageResource(recruitData?.thumbnail!!)
        rtBinding.tvRtTitle.text = recruitData.title
        rtBinding.tvRtDetail.text = recruitData.detail

        val getRole = resources.getStringArray(R.array.select_role)
        val arrayAdapter = ArrayAdapter(this,R.layout.dropdown_item, getRole)
        rtBinding.actvRole.setAdapter(arrayAdapter)

        //드롭박스 선택 인원수 확인
        rtBinding.actvRole.setOnItemClickListener { adapterView, view, i, l ->
            Log.d("로그","역할 선택 : $i")
        }

    }
}