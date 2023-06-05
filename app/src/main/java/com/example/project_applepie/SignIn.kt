package com.example.project_applepie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.example.project_applepie.databinding.ActivitySignInBinding
import com.example.project_applepie.model.dao.js_signIn
import com.example.project_applepie.retrofit.ApiService
import com.example.project_applepie.retrofit.domain.LoginData
import com.example.project_applepie.sharedpref.SharedPref
import com.example.project_applepie.utils.Url
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// 로그인 절차를 위한 변수
var login_emailPass = 1
var login_pwPass = 1
class SignIn : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 입력란 확인 코드
        signInEmailFocusListener()
        signInPasswordFocusListener()

        // Retrofit 연동
        val url = Url.BASE_URL
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var server = retrofit.create(ApiService::class.java)

        // 아이디 찾기 버튼 클릭 시
        binding.findId.setOnClickListener {
            val intent = Intent(this, FindMyId::class.java)
            startActivity(intent)
        }
        // 비밀번호 찾기 버튼 클릭 시
        binding.findPw.setOnClickListener {
            val intent = Intent(this, FindMyPw::class.java)
            startActivity(intent)
        }

        // 상단 X 버튼
        binding.returnMain.setOnClickListener {
            finish()
        }

        // 로그인 버튼
        binding.logInUser.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)

//            if(login_emailPass == 0 && login_pwPass == 0){
            // 로그인 시 ID & PW 얻기
            val uEmail: String = binding.etUsermail.text.toString()
            val uPw: String = binding.etUserpw.text.toString()

            val logInModel : js_signIn = js_signIn(uEmail, uPw)

            server.logIn(logInModel).enqueue(object : Callback<LoginData>{
                override fun onFailure(call: Call<LoginData>, t: Throwable) {
                    Log.d("서버 연동", "로그인 실패")
                    Log.d("서버 연동","${t.message}")
                    Toast.makeText(this@SignIn, "서버 오류! 로그인 실패", Toast.LENGTH_LONG).show()
                }
                override fun onResponse(call: Call<LoginData>, response: Response<LoginData>) {
                    val userLogin = response.body()
                    Log.d("로그","${response.body().toString()}")
                    if(userLogin?.status == 200){

                        SharedPref.setUserId(this@SignIn, userLogin.uid) // uid 설정
                        SharedPref.setPid(this@SignIn, userLogin.pid) // pid 설정
                        Log.d("로그인 성공", "로그인 성공 $uEmail, $uPw")

//                        val isFirst = SharedPref.getFirstTime(this@SignIn, "first")
//                        if(!isFirst){
//                            SharedPref.setFirstTime(this@SignIn, "first", true)
//                            val intent = Intent(this@SignIn, createDetailProfile::class.java)
//                            startActivity(intent)
//                            finish()
//                        } else {
//                            val intent = Intent(this@SignIn, HomeActivity::class.java)
//                            startActivity(intent)
//                            finish()
//                        }
                        startActivity(intent)
                        finish()

                    } else {
                        Toast.makeText(this@SignIn, "가입된 계정이 아닙니다!", Toast.LENGTH_LONG).show()
                        Log.d("로그인 실패", "$response")
                    }
                }
            })
        // }
//            startActivity(intent)
//            finish()
        }

        // 회원가입 문구 클릭 시
        binding.goSignUp.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }


    // -------------------------------------------------------------------------------------------------------------------------------------------------


    // 이메일 유효성 검증
    private fun signInEmailFocusListener() {
        binding.etUsermail.setOnFocusChangeListener{ _, focused ->
            if(!focused){
                binding.tlUsermail.helperText = validSIEmail()
            }
        }
    }

    private fun validSIEmail(): String? {
        val emailText = binding.etUsermail.text.toString()
        if(!emailText.matches("^[a-zA-X0-9]+@[a-zA-Z0-9]+.[a-zA-Z0-9]+$".toRegex())){
            login_emailPass = 1
            return "잘못된 이메일 형식입니다."
        }
        else {
            login_emailPass = 0 }
        return null
    }

    // 비밀번호 유효성 검증
    private fun signInPasswordFocusListener(){
        binding.etUserpw.setOnFocusChangeListener { _, focused ->
            if(!focused){
                binding.tlUserpw.helperText = validSIPassword()
            }
        }
    }
    private fun validSIPassword(): String? {
        val passwordText = binding.etUserpw.text.toString()
        if(passwordText.length < 6 || passwordText.length > 15){
            login_pwPass = 1
            return "6자 이상, 15자 이하로 입력해주세요."
        } else { login_pwPass = 0 }

        if(!passwordText.matches(".*[!@#\$%^&+=].*".toRegex())) {
            login_pwPass = 1
            return "특수문자 1개 이상을 포함해야합니다. (@#\$%^&+=)"
        } else { login_pwPass = 0 }

        return null
    }
}