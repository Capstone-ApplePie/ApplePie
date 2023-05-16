package com.example.project_applepie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.project_applepie.databinding.ActivityModifyProfileBinding
import com.example.project_applepie.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ModifyProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mpBinding = ActivityModifyProfileBinding.inflate(layoutInflater)
        setContentView(mpBinding.root)

        // 사용자 지역
        val uArea : String = mpBinding.modArea.toString()
        // 사용자 출신 대학교
        val uCollege : String = mpBinding.modColleage.toString()

        // 사용자가 대학교에서 받은 학점 (숫자로만 입력 받도록 수정 필요) <------------------------------------------------------------
        val uGrade = mpBinding.modMyScore.toString().toFloat()

        // 학년 선택 -> 기본값 : 2학년 (나중에 비우고 선택 안하면 넘어가지 않도록 수정 필요) <-------------------------------------------
        var uGrader : String = "2학년"
        mpBinding.toggleButton.check(R.id.btn_grade2)
        mpBinding.btnGrade1.setOnClickListener {
            uGrader = "1학년"
        }
        mpBinding.btnGrade2.setOnClickListener {
            uGrader = "2학년"
        }
        mpBinding.btnGrade3.setOnClickListener {
            uGrader = "3학년"
        }
        mpBinding.btnGrade4.setOnClickListener {
            uGrader = "4학년"
        }

        // 사용자 깃허브 주소
        val uGit : String = mpBinding.uGithub.toString()

        // 사용자가 사용하는 프로그램 언어 선택


        // 사용자가 사용하는 프로그램 선택
        val getFramework = resources.getStringArray(R.array.create_profile_framework)
        val arrayAdapter2 = ArrayAdapter(this,R.layout.dropdown_item, getFramework)
        mpBinding.acFramework.setAdapter(arrayAdapter2)

        var uFramework : String = "None"
        mpBinding.acFramework.setOnItemClickListener { adapterView, view, position, id ->
            uFramework = getFramework[position].toString()
//            Toast.makeText(this, "$uFramework", Toast.LENGTH_SHORT).show()
        }

        mpBinding.modSuccessBtn.setOnClickListener {
//            // Retrofit 연동
//            val url = "여기에 서버 주소 입력"
//            val retrofit = Retrofit.Builder()
//                .baseUrl(url)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//
//            var server = retrofit.create(ApiService::class.java)
//
//            server.modifyProfile(uArea, uCollege, uGrade, uGrader, uGit, uLanguage, uFramework).enqueue(object:Callback<LoginData> {
//                override fun onFailure(call: Call<LoginData>, t: Throwable) {
//                    Log.d("프로필수정 실패", "프로필 수정 실패")
//                    Toast.makeText(this@ModifyProfileActivity, "서버 오류! 프로필 수정 실패", Toast.LENGTH_LONG).show()
//                }
//
//                override fun onResponse(call: Call<LoginData>, response: Response<LoginData>) {
//                    Toast.makeText(this@ModifyProfileActivity, "프로필 생성을 완료했습니다.", Toast.LENGTH_SHORT).show()
//                    val intent = Intent(this@ModifyProfileActivity, PersonalInformation::class.java)
//                    startActivity(intent)
//                    finish()
//                }
//                }
//            )

            val intent = Intent(this, PersonalInformation::class.java)
            startActivity(intent)
            finish()
        }
    }
}