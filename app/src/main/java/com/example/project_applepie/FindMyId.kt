package com.example.project_applepie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.project_applepie.databinding.ActivityFindMyIdBinding
import com.example.project_applepie.databinding.ActivitySignInBinding

class FindMyId : AppCompatActivity() {

    private lateinit var binding: ActivityFindMyIdBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindMyIdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrofit 연동
//        val url = "여기에 서버 주소 입력"
//        val retrofit = Retrofit.Builder()
//            .baseUrl(url)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        var server = retrofit.create(LoginService::class.java)


        // 화면 상단 'X' 버튼 클릭
        binding.returnLogIn.setOnClickListener {
            finish()
        }

        // 이메일 주소 '인증' 버튼 클릭
        binding.certifiedBtn.setOnClickListener {

        }

        // 마지막 '확인' 버튼 클릭
        binding.check.setOnClickListener {
//            val intent = Intent(this, SignIn::class.java)
//            startActivity(intent)
//            finish()
        }
    }
}