package com.example.project_applepie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.project_applepie.databinding.ActivityCreateProfileBinding
import com.example.project_applepie.retrofit.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CreateProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val cpBinding = ActivityCreateProfileBinding.inflate(layoutInflater)
        setContentView(cpBinding.root)



        val getLanguage = resources.getStringArray(R.array.create_profile_language)
        val arrayAdapter = ArrayAdapter(this,R.layout.dropdown_item, getLanguage)
        cpBinding.acLanguage.setAdapter(arrayAdapter)

        val getFramework = resources.getStringArray(R.array.create_profile_language)
        val arrayAdapter2 = ArrayAdapter(this,R.layout.dropdown_item, getFramework)
        cpBinding.acLanguage.setAdapter(arrayAdapter)

        // 학년 기본값 : 2학년
        var uGrade : String = "2학년"
        cpBinding.toggleButton.check(R.id.btn_grade2)
        cpBinding.btnGrade1.setOnClickListener {
            uGrade = "1학년"
        }
        cpBinding.btnGrade2.setOnClickListener {
            uGrade = "2학년"
        }
        cpBinding.btnGrade3.setOnClickListener {
            uGrade = "3학년"
        }
        cpBinding.btnGrade4.setOnClickListener {
            uGrade = "4학년"
        }

        cpBinding.createProfile.setOnClickListener {
            // Retrofit 연동
//            val url = "여기에 서버 주소 입력"
//            val retrofit = Retrofit.Builder()
//                .baseUrl(url)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//
//            var server = retrofit.create(ApiService::class.java)

            Toast.makeText(this, "프로필 생성을 완료했습니다.", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}