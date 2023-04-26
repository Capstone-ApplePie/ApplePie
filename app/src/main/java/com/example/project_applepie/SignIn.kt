package com.example.project_applepie

import android.content.Intent
import android.os.Build.VERSION_CODES.O
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.util.Log
import android.widget.*
import com.example.project_applepie.databinding.ActivitySignInBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SignIn : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrofit 연동
//        val url = "여기에 서버 주소 입력"
//        val retrofit = Retrofit.Builder()
//            .baseUrl(url)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        var server = retrofit.create(LoginService::class.java)

        // 아이디 찾기 추가...
        /*
        findViewById<EditText>(R.id.findId).setOnClickListener {
        }*/

        // 상단 X 버튼
        binding.returnMain.setOnClickListener {
            finish()
        }

        // 로그인 버튼
        binding.logInUser.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)

            // 로그인 시 ID & PW 얻기
            val uId: String = binding.etUsermail.text.toString()
            val uPw: String = binding.etUserpw.text.toString()

//            server.logIn(uId, uPw).enqueue(object : Callback<LoginData>{
//                override fun onFailure(call: Call<LoginData>, t: Throwable) {
//                    Log.d("로그인 실패", "로그인 실패")
//                    Toast.makeText(this@SignIn, "서버 오류! 로그인 실패", Toast.LENGTH_LONG).show()
//                }
//                override fun onResponse(call: Call<LoginData>, response: Response<LoginData>) {
//                    val userLogin = response.body()
//                    if(userLogin?.status == 201){
//                        Log.d("로그인 성공", "로그인 성공 $uId, $uPw")
//                        startActivity(intent)
//                        finish()
//                    } else {
//                        Toast.makeText(this@SignIn, "가입된 계정이 아닙니다!", Toast.LENGTH_LONG).show()
//                    }
//                }
//            })

            startActivity(intent)
            finish()
        }

        // 회원가입 문구 클릭 시
        binding.goSignUp.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }
}