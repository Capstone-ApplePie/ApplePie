package com.example.project_applepie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.project_applepie.databinding.ActivityCreateProfileBinding
import com.example.project_applepie.model.dao.sinup
import com.example.project_applepie.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// 프로필 생성 절차를 위한 변수
var createProfile_regionPass = 1
var createProfile_collegePass = 1
var createProfile_emailPass = 1
var createProfile_gradePass = 1


class CreateProfile : AppCompatActivity() {

    private lateinit var cpBinding: ActivityCreateProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cpBinding = ActivityCreateProfileBinding.inflate(layoutInflater)
        setContentView(cpBinding.root)

        // 입력란 확인 코드
        regionFocusListener()
        collegeFocusListener()
        emailFocusListener()

        // 회원가입 정보
        val uEmail= intent.getStringExtra("uEmail")
        val uPw = intent.getStringExtra("uPw")
        val uName = intent.getStringExtra("uName")
        val uNickname = intent.getStringExtra("uNickname")
        val uCorp = intent.getBooleanExtra("uCorp", false)
        val uBirth = intent.getStringExtra("uBirth")
        val uGender = intent.getStringExtra("uGender")

        // 사용자 지역
        var uArea : String = cpBinding.area.toString()
        // 사용자 출신 대학교
        var uCollege : String = cpBinding.colleague.toString()

        // 사용자가 대학교에서 받은 학점 (숫자로만 입력 받도록 수정 필요) <------------------------------------------------------------
        var uGrade : Float = 4.5F
        try{
            uGrade = cpBinding.myScore.toString().toFloat()
        } catch (e: NumberFormatException){
            // 예외 처리 필요
        }

        // 사용자가 다니는 대학의 총 학점 (숫자로만 입력 받도록 수정 필요) <-----------------------------------------------------------
        var uTotalGrade : Float = 4.5F
        try{
            uTotalGrade = cpBinding.maxScore.toString().toFloat()
        } catch (e: java.lang.NumberFormatException){
            // 예외 처리 필요
        }


        // 학년 선택 -> 기본값 : 2학년 (나중에 비우고 선택 안하면 넘어가지 않도록 수정 필요) <-------------------------------------------
        var uGrader : String = "2학년"
        cpBinding.toggleButton.check(R.id.btn_grade2)
        cpBinding.btnGrade1.setOnClickListener {
            uGrader = "1학년"
        }
        cpBinding.btnGrade2.setOnClickListener {
            uGrader = "2학년"
        }
        cpBinding.btnGrade3.setOnClickListener {
            uGrader = "3학년"
        }
        cpBinding.btnGrade4.setOnClickListener {
            uGrader = "4학년"
        }

        // 사용자 깃허브 주소
        var uGit : String = cpBinding.writeGit.toString()

        // 사용자가 사용하는 프로그램 언어 선택
        cpBinding.chLang.setOnCheckedStateChangeListener { _, checked ->

            Log.d("test", "Click: $checked")
        }

        var uLanguage : List<Int> = listOf(0)

        // 사용자가 사용하는 프로그램 선택
        val getFramework = resources.getStringArray(R.array.create_profile_framework)
        val arrayAdapter2 = ArrayAdapter(this,R.layout.dropdown_item, getFramework)
        cpBinding.acFramework.setAdapter(arrayAdapter2)

        var uFramework : String = "None"
        cpBinding.acFramework.setOnItemClickListener { adapterView, view, position, id ->
            uFramework = getFramework[position].toString()
//            Toast.makeText(this, "$uFramework", Toast.LENGTH_SHORT).show()
        }

        cpBinding.createProfile.setOnClickListener {
            // Retrofit 연동
//            val url = ""
//            val retrofit = Retrofit.Builder()
//                .baseUrl(url)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//
//            var server = retrofit.create(ApiService::class.java)
//
//            val signupModal: sinup = sinup(uEmail, uPw, uName, uNickname, uCorp, uBirth, uGender,
//                uArea, uCollege, uGrade, uTotalGrade, uGrader, uGit, uLanguage, uFramework)
//
//            Log.d("로그","signupModel : $signupModal")
//
//            // 회원가입 정보 null 처리 필요 <-------------------------------------------------------------------------------
//            if (uEmail != null && uPw != null && uName != null && uNickname!=null && uBirth!=null && uGender!=null
//                && uGrade != null && uTotalGrade != null) {
//
//                server.signUp(signupModal).enqueue(object :
//                    Callback<LoginData> {
//                    override fun onFailure(call: Call<LoginData>, t: Throwable) {
//                        Log.d("회원가입 실패", "회원가입 실패")
//                        Log.d("회원가입 실패", "$t")
//                        Toast.makeText(this@CreateProfile, "서버 오류! 회원가입 실패", Toast.LENGTH_LONG).show()
//                    }
//
//                    override fun onResponse(call: Call<LoginData>, response: Response<LoginData>) {
//                        Log.d("로그","response : $response")
//                        Log.d("로그","call : $call")
//                        Toast.makeText(this@CreateProfile, "프로필 생성을 완료했습니다.", Toast.LENGTH_SHORT).show()
//                        val intent = Intent(this@CreateProfile, SignIn::class.java)
//                        startActivity(intent)
//                        finish()
//                    }
//                } )
//            }

            Toast.makeText(this, "프로필 생성을 완료했습니다.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@CreateProfile, SignIn::class.java)
            startActivity(intent)
            finish()
        }
    }


    // -------------------------------------------------------------------------------------------------------------------------------------------------


    // 지역 확인
    private fun regionFocusListener(){
        cpBinding.area.setOnFocusChangeListener { _, focused ->
            if(!focused){
                cpBinding.etCpRegion.helperText = validRegion()
            }
        }
    }
    private fun validRegion(): String?
    {
        val nameText = cpBinding.area.text.toString()

        if(!nameText.matches("^[ㄱ-ㅎ가-힣]*\$".toRegex())){
            createProfile_regionPass = 1
            return "한글만 입력해주세요."
        } else { createProfile_regionPass = 0 }

        return null
    }

    // 대학교 확인
    private fun collegeFocusListener(){
        cpBinding.colleague.setOnFocusChangeListener { _, focused ->
            if(!focused){
                cpBinding.etCpCollege.helperText = validName()
            }
        }
    }
    private fun validName(): String?
    {
        val nameText = cpBinding.colleague.text.toString()

        if(!nameText.matches("^[ㄱ-ㅎ가-힣]*\$".toRegex())){
            createProfile_collegePass = 1
            return "한글만 입력해주세요."
        } else { createProfile_collegePass = 0 }

        return null
    }

    // 학점 검증
//    private fun gradeFocusListener() {
//        cpBinding.myScore.setOnFocusChangeListener{ _, focused ->
//            if(!focused){
//                cpBinding.helperTextGrade.helperText = validGrade()
//            }
//        }
//    }
//
//    private fun validGrade(): String? {
//        val gradeText = cpBinding.myScore.text.toString()
//        if()
//    }

    // 이메일 유효성 검증
    private fun emailFocusListener() {
        cpBinding.writeGit.setOnFocusChangeListener{ _, focused ->
            if(!focused){
                cpBinding.etCpGithub.helperText = validEmail()
            }
        }
    }

    private fun validEmail(): String? {
        val emailText = cpBinding.writeGit.text.toString()
        if(!emailText.matches("^[a-zA-X0-9]+@[a-zA-Z0-9]+.[a-zA-Z0-9]+$".toRegex())){
            createProfile_emailPass = 1
            return "잘못된 이메일 형식입니다."
        }
        else {
            createProfile_emailPass = 0 }
        return null
    }
}