package com.example.project_applepie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.project_applepie.databinding.ActivityCreateDetailProfileBinding
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

class createDetailProfile : AppCompatActivity() {

    private lateinit var cdpBinding: ActivityCreateDetailProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cdpBinding = ActivityCreateDetailProfileBinding.inflate(layoutInflater)
        setContentView(cdpBinding.root)

        // 상단 뒤로가기 버튼 활성화
        cdpBinding.topAppBar.setNavigationOnClickListener { _ ->
            finish()
        }

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
        cdpBinding.swOutsourcing.setOnCheckedChangeListener{ _, b ->
            uOpenOut = if(!b){
                Log.d("Out", "꺼짐")
                0
            } else {
                1
            }
        }
        cdpBinding.swLesson.setOnCheckedChangeListener { _, b ->
            uOpenLes = if(!b){
                Log.d("Les", "꺼짐")
                0
            } else {
                1
            }
        }
        cdpBinding.swProject.setOnCheckedChangeListener { _, b ->
            uOpenPro = if(!b){
                Log.d("Pro", "꺼짐")
                0
            } else {
                1
            }
        }

        cdpBinding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId){
                R.id.item_skip -> {
                    val pid = SharedPref.getPid(this@createDetailProfile)
//                    val pid = "2"
                    val url = Url.BASE_URL
                    val retrofit = Retrofit.Builder()
                        .baseUrl(url)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()

                    var server = retrofit.create(ApiService::class.java)

                    val cdpModelOut = js_CDP(0, uSelfIntroOut, uIntroOut, uCareer, uOpenOut)
                    val cdpModelLes = js_CDP(1, uSelfIntroLes, uIntroLes, uSubject, uOpenLes)
                    val cdpModelPro = js_CDP(2, uSelfIntroPro, uIntroPro, uPart, uOpenPro)

                    var checkOne = 0
                    var checkTwo = 0
                    var checkThr = 0

                    server.createDetailProfile(pid, cdpModelOut).enqueue(object : Callback<personalDetailProfile>{
                        override fun onResponse(call: Call<personalDetailProfile>, response: Response<personalDetailProfile>
                        ) {
                            checkOne = 1
                        }

                        override fun onFailure(call: Call<personalDetailProfile>, t: Throwable) {
                            checkOne = 0
                            Log.d("건너뛰기 1번 오류", "$t")
                        }
                    })
                    server.createDetailProfile(pid, cdpModelLes).enqueue(object : Callback<personalDetailProfile>{
                        override fun onResponse(call: Call<personalDetailProfile>, response: Response<personalDetailProfile>
                        ) {
                            checkTwo = 1
                        }

                        override fun onFailure(call: Call<personalDetailProfile>, t: Throwable) {
                            checkTwo = 0
                            Log.d("건너뛰기 2번 오류", "$t")
                        }
                    })
                    server.createDetailProfile(pid, cdpModelPro).enqueue(object : Callback<personalDetailProfile>{
                        override fun onResponse(call: Call<personalDetailProfile>, response: Response<personalDetailProfile>
                        ) {
                            checkThr = 1
                        }

                        override fun onFailure(call: Call<personalDetailProfile>, t: Throwable) {
                            checkThr = 0
                            Log.d("건너뛰기 3번 오류", "$t")
                        }
                    })

                    if(checkOne == 1 && checkTwo == 1 && checkThr == 1){
                        val intent = Intent(this@createDetailProfile, SignIn::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Log.d("건너뛰기 최종 오류", "오류 발생")
                    }

                    true
                }
                else -> false
            }
        }

        cdpBinding.createDetailProfile.setOnClickListener {
            // 전송을 위해 pid 가져오기
//            val pid = SharedPref.getPid(this@createDetailProfile)
            val pid = "1"

            // Retrofit 연동
            val url = Url.BASE_URL
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            var server = retrofit.create(ApiService::class.java)

//            SharedPref.setFirstTime(this, "first", true)

            uSelfIntroOut = cdpBinding.outTitle.text.toString()
            uIntroOut = cdpBinding.outWriteSelfIntro.text.toString()
            uCareer = cdpBinding.outWriteCareer.text.toString()

            uSelfIntroLes = cdpBinding.lesTitle.text.toString()
            uIntroLes = cdpBinding.lesWriteText.text.toString()
            uSubject = cdpBinding.lesWriteSub.text.toString()

            uSelfIntroPro = cdpBinding.proTitle.text.toString()
            uIntroPro = cdpBinding.proWriteText.text.toString()
            uPart = cdpBinding.proWritePart.text.toString()

            val cdpModelOut = js_CDP(0, uSelfIntroOut, uIntroOut, uCareer, uOpenOut)
            val cdpModelLes = js_CDP(1, uSelfIntroLes, uIntroLes, uSubject, uOpenLes)
            val cdpModelPro = js_CDP(2, uSelfIntroPro, uIntroPro, uPart, uOpenPro)

            var checkOne = 0
            var checkTwo = 0
            var checkThr = 0

            server.createDetailProfile(pid, cdpModelOut).enqueue(object : Callback<personalDetailProfile>{
                override fun onResponse(call: Call<personalDetailProfile>, response: Response<personalDetailProfile>
                ) {
                    val findError = response.body()
                    if(findError?.status == 200){
                        Log.d("세부 프로필 성공?", "${response.body()?.status}")
                        Log.d("세부 프로필 성공?", "${response.body()?.message}")
                        checkOne = 1
//                        val intent = Intent(this@createDetailProfile, SignIn::class.java)
//                        startActivity(intent)
//                        finish()
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

            server.createDetailProfile(pid, cdpModelLes).enqueue(object : Callback<personalDetailProfile>{
                override fun onResponse(call: Call<personalDetailProfile>, response: Response<personalDetailProfile>
                ) {
                    val findError = response.body()
                    if(findError?.status == 200){
                        Log.d("세부 프로필 성공?", "${response.body()?.status}")
                        Log.d("세부 프로필 성공?", "${response.body()?.message}")
                        checkTwo = 1
//                        val intent = Intent(this@createDetailProfile, SignIn::class.java)
//                        startActivity(intent)
//                        finish()
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

            server.createDetailProfile(pid, cdpModelPro).enqueue(object : Callback<personalDetailProfile>{
                override fun onResponse(call: Call<personalDetailProfile>, response: Response<personalDetailProfile>
                ) {
                    val findError = response.body()
                    if(findError?.status == 200){
                        Log.d("세부 프로필 성공?", "${response.body()?.status}")
                        Log.d("세부 프로필 성공?", "${response.body()?.message}")
                        checkThr = 3
//                        val intent = Intent(this@createDetailProfile, SignIn::class.java)
//                        startActivity(intent)
//                        finish()
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

            if(checkOne == 1 && checkTwo == 1 && checkThr == 1){
                val intent = Intent(this@createDetailProfile, SignIn::class.java)
                startActivity(intent)
                finish()
            }
        }

    }
}