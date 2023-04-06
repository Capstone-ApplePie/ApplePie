package com.example.project_applepie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.example.project_applepie.databinding.ActivitySignupBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

// 회원가입 절차를 위한 변수
var namePass = 1
var emailPass = 1
var pwPass = 1
var pwCheck = 1

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 입력란 확인 코드
        emailFocusListener()
        passwordFocusListener()
        pwCheckFocusListener()

        // 기본 설정 = 회사 아님
        binding.compNo.isChecked = true

        // 상단 X 버튼
        binding.returnMain.setOnClickListener {
            finish()
        }

        // 회원가입 버튼 클릭
        binding.signUpBtn.setOnClickListener {
            var intent = Intent(this, SignIn::class.java)
            Toast.makeText(this@SignupActivity, "회원가입이 완료되었습니다.", Toast.LENGTH_LONG).show()
            startActivity(intent)
            finish()
        }
    }



    // 이메일 유효성 검증
    private fun emailFocusListener() {
        binding.etUsermail.setOnFocusChangeListener{ _, focused ->
            if(!focused){
                binding.tlUsermail.helperText = validEmail()
            }
        }
    }
    private fun validEmail(): String? {
        val emailText = binding.etUsermail.text.toString()
        if(!emailText.matches("^[a-zA-X0-9]+@[a-zA-Z0-9]+.[a-zA-Z0-9]+$".toRegex())){
            emailPass = 1
            return "잘못된 이메일 형식입니다."
        }
        else {
            emailPass = 0 }
        return null
    }

    // 비밀번호 유효성 검증
    private fun passwordFocusListener(){
        binding.etUserpw.setOnFocusChangeListener { _, focused ->
            if(!focused){
                binding.tlUserpw.helperText = validPassword()
            }
        }
    }
    private fun validPassword(): String? {
        val passwordText = binding.etUserpw.text.toString()
        if(passwordText.length < 6 || passwordText.length > 15){
            pwPass = 1
            return "6자 이상, 15자 이하로 입력해주세요."
        } else { pwPass = 0 }

        if(!passwordText.matches(".*[@#\$%^&+=].*".toRegex())) {
            pwPass = 1
            return "특수문자 1개 이상을 포함해야합니다. (@#\$%^&+=)"
        } else { pwPass = 0 }

        return null
    }

    private fun pwCheckFocusListener(){
        binding.etUserpwcheck.setOnFocusChangeListener { _, focused ->
            if(!focused){
                binding.tlUserpwcheck.helperText = validPwCheck()
            }
        }
    }

    private fun validPwCheck(): String? {
        val psText = binding.etUserpw.text.toString()
        val psCheckText = binding.etUserpwcheck.text.toString()

        if(psText != psCheckText){
            pwCheck = 1
            return "비밀번호가 일치하지 않습니다."
        } else { pwCheck = 0 }

        return null
    }
}