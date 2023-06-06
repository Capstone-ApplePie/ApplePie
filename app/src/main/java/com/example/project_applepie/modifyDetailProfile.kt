package com.example.project_applepie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.project_applepie.databinding.ActivityModifyDetailProfileBinding
import com.example.project_applepie.model.dao.js_CDP
import com.example.project_applepie.model.dao.personalDetailProfile
import com.example.project_applepie.retrofit.ApiService
import com.example.project_applepie.sharedpref.SharedPref
import com.example.project_applepie.utils.Url
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class modifyDetailProfile : AppCompatActivity() {

    private lateinit var mdpBinding: ActivityModifyDetailProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mdpBinding = ActivityModifyDetailProfileBinding.inflate(layoutInflater)
        setContentView(mdpBinding.root)

        // 상단 뒤로가기 버튼 활성화
        mdpBinding.topAppBar.setNavigationOnClickListener { _ ->
            finish()
        }

        // 서버 연동
        val url = Url.BASE_URL
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var server = retrofit.create(ApiService::class.java)

        val pid = SharedPref.getPid(this@modifyDetailProfile)

        // 사용자 입력 정보 받아올 변수 모음 (소개, 희망 분야, 여부, 과목, 여부, 경력, 여부)
        var briefIntroOut = "-" // outTitle
        var introOut : String = "-" // outWriteSelfIntro
        var careerOut : String = "-" // outWriteCareer
        var openOut : Int = 0

        var briefIntroLes : String = "-"
        var introLes : String = "-"
        var subjectLes : String ="-"
        var openLes : Int = 0

        var briefIntroPro : String = "-"
        var introPro : String = "-"
        var partPro : String = "-"
        var openPro : Int = 0

        // 사용자가 처음 입력한 세부 프로필 정보 불러오기
        server.searchProfileDetails(pid).enqueue(object : Callback<personalDetailProfile>{
            override fun onResponse(call: Call<personalDetailProfile>, response: Response<personalDetailProfile>) {
                Log.d("로그","${response.body().toString()}")
                val userOut = response.body()?.outsourcing
                val outBriefIntro = userOut?.get("introduce").toString()
                mdpBinding.outTitle.setText(outBriefIntro.replace("\"",""))

                val outIntro = userOut?.get("outsourcingSelf").toString()
                mdpBinding.outWriteSelfIntro.setText(outIntro.replace("\"",""))

                val outCareer = userOut?.get("career").toString()
                mdpBinding.outWriteCareer.setText(outCareer.replace("\"",""))

                var outUserOpen = userOut?.get("open").toString().toBoolean()
                when(outUserOpen){
                    true -> {
                        mdpBinding.swOutsourcing.isChecked = true
                    }
                    false -> {
                        mdpBinding.swOutsourcing.isChecked = false
                    }
                }

                val userLes = response.body()?.lesson
                val lesBriefIntro = userLes?.get("introduce").toString()
                mdpBinding.lesTitle.setText(lesBriefIntro.replace("\"",""))

                val lesIntro = userLes?.get("lessonSelf").toString()
                mdpBinding.lesWriteText.setText(lesIntro.replace("\"",""))

                val lesCareer = userLes?.get("subject").toString()
                mdpBinding.lesWriteSub.setText(lesCareer.replace("\"",""))

                var lesUserOpen = userLes?.get("open").toString().toBoolean()
                when(lesUserOpen){
                    true -> {
                        mdpBinding.swLesson.isChecked = true
                    }
                    false -> {
                        mdpBinding.swLesson.isChecked = false
                    }
                }

                val userPro = response.body()?.project
                val proBriefIntro = userPro?.get("introduce").toString()
                mdpBinding.proTitle.setText(proBriefIntro.replace("\"",""))

                val proIntro = userPro?.get("projectSelf").toString()
                mdpBinding.proWriteText.setText(proIntro.replace("\"",""))

                val proCareer = userPro?.get("part").toString()
                mdpBinding.proWritePart.setText(proCareer.replace("\"",""))

                var proUserOpen = userPro?.get("open").toString().toBoolean()
                when(proUserOpen){
                    true -> {
                        mdpBinding.swProject.isChecked = true
                    }
                    false -> {
                        mdpBinding.swProject.isChecked = false
                    }
                }
            }

            override fun onFailure(call: Call<personalDetailProfile>, t: Throwable) {
                Log.d("실패 로그","$t")
            }
        })

        // 사용자 입력 정보 받아올 변수 모음 (소개, 희망 분야, 여부, 과목, 여부, 경력, 여부)
        var uSelfIntroOut : String = "-" // outTitle
        var uIntroOut : String = "-" // outWriteSelfIntro
        var uCareer : String = "-" // outWriteCareer
        var uOpenOut : Int = 0

        var uSelfIntroLes : String = "-"
        var uIntroLes : String = "-"
        var uSubject : String ="-"
        var uOpenLes : Int = 0

        var uSelfIntroPro : String = "-"
        var uIntroPro : String = "-"
        var uPart : String = "-"
        var uOpenPro : Int = 0

        // 공개 여부
        mdpBinding.swOutsourcing.setOnCheckedChangeListener{ _, b ->
            uOpenOut = if(!b){
                Log.d("Out", "꺼짐")
                0
            } else {
                1
            }
        }
        mdpBinding.swLesson.setOnCheckedChangeListener { _, b ->
            uOpenLes = if(!b){
                Log.d("Les", "꺼짐")
                0
            } else {
                1
            }
        }
        mdpBinding.swProject.setOnCheckedChangeListener { _, b ->
            uOpenPro = if(!b){
                Log.d("Pro", "꺼짐")
                0
            } else {
                1
            }
        }

        mdpBinding.modifyDetailProfile.setOnClickListener {
            uSelfIntroOut = mdpBinding.outTitle.text.toString()
            uIntroOut = mdpBinding.outWriteCareer.text.toString() // 서로 바뀜 outWriteCareer
            uCareer = mdpBinding.outWriteSelfIntro.text.toString()

            uSelfIntroLes = mdpBinding.lesTitle.text.toString()
            uIntroLes = mdpBinding.lesWriteSub.text.toString() // lesWriteSub
            uSubject = mdpBinding.lesWriteText.text.toString()

            uSelfIntroPro = mdpBinding.proTitle.text.toString()
            uIntroPro = mdpBinding.proWritePart.text.toString() // proWritePart
            uPart = mdpBinding.proWriteText.text.toString()

            val mdpModelOut = js_CDP(0, uSelfIntroOut, uIntroOut, uCareer, uOpenOut)
            val mdpModelLes = js_CDP(1, uSelfIntroLes, uIntroLes, uSubject, uOpenLes)
            val mdpModelPro = js_CDP(2, uSelfIntroPro, uIntroPro, uPart, uOpenPro)

            server.modifyDetailProfile(pid, mdpModelOut).enqueue(object :
                Callback<personalDetailProfile> {
                override fun onResponse(call: Call<personalDetailProfile>, response: Response<personalDetailProfile>
                ) {
                    val findError = response.body()
                    if(findError?.status == 200){
                        Log.d("세부 프로필 성공?", "${response.body()?.status}")
                        Log.d("세부 프로필 성공?", "${response.body()?.message}")
                    }
                    else {
                        Log.d("문제찾아 삼만리", "${findError?.status} + ${findError?.message}")
                        Log.d("문제찾아 삼만리", "$uSelfIntroOut, $uIntroOut, $uCareer")
                    }
                }

                override fun onFailure(call: Call<personalDetailProfile>, t: Throwable) {
                    Log.d("세부 프로필 서버통신 실패", "오류 발생, $t")
                }
            })

            server.modifyDetailProfile(pid, mdpModelLes).enqueue(object :
                Callback<personalDetailProfile> {
                override fun onResponse(call: Call<personalDetailProfile>, response: Response<personalDetailProfile>
                ) {
                    val findError = response.body()
                    if(findError?.status == 200){
                        Log.d("세부 프로필 성공?", "${response.body()?.status}")
                        Log.d("세부 프로필 성공?", "${response.body()?.message}")
                    }
                    else {
                        Log.d("문제찾아 삼만리", "${findError?.status} + ${findError?.message}")
                        Log.d("문제찾아 삼만리", "$uSelfIntroOut, $uIntroOut, $uCareer")
                    }
                }

                override fun onFailure(call: Call<personalDetailProfile>, t: Throwable) {
                    Log.d("세부 프로필 서버통신 실패", "오류 발생, $t")
                }
            })

            server.modifyDetailProfile(pid, mdpModelPro).enqueue(object :
                Callback<personalDetailProfile> {
                override fun onResponse(call: Call<personalDetailProfile>, response: Response<personalDetailProfile>
                ) {
                    val findError = response.body()
                    if(findError?.status == 200){
                        Log.d("세부 프로필 성공?", "${response.body()?.status}")
                        Log.d("세부 프로필 성공?", "${response.body()?.message}")
                    }
                    else {
                        Log.d("문제찾아 삼만리", "${findError?.status} + ${findError?.message}")
                        Log.d("문제찾아 삼만리", "$uSelfIntroOut, $uIntroOut, $uCareer")
                    }
                }

                override fun onFailure(call: Call<personalDetailProfile>, t: Throwable) {
                    Log.d("세부 프로필 서버통신 실패", "오류 발생, $t")
                }
            })

            Toast.makeText(this@modifyDetailProfile, "수정이 완료되었습니다.", Toast.LENGTH_SHORT).show()

            val intent = Intent(this@modifyDetailProfile, HomeActivity::class.java)
            val changeView = 100
            intent.putExtra("chgV", changeView)
            startActivity(intent)
            finish()
        }
    }
}