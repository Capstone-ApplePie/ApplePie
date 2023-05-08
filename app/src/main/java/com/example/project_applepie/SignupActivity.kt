package com.example.project_applepie

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.project_applepie.databinding.ActivitySignupBinding
import com.example.project_applepie.retrofit.ApiService
import com.example.project_applepie.utils.API
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.Instant
import java.util.*

// 회원가입 절차를 위한 변수
var namePass = 1
var emailPass = 1
var pwPass = 1
var pwCheck = 1

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 입력란 확인 코드
        nameFocusListener()
        emailFocusListener()
        passwordFocusListener()
        pwCheckFocusListener()

        // Retrofit 연동
//        val url = "여기에 서버 주소 입력"
//        val retrofit = Retrofit.Builder()
//            .baseUrl(url)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        var server = retrofit.create(ApiService::class.java)

        // 기본 설정 = 회사 아님
        binding.compNo.isChecked = true
        binding.rbMen.isChecked = true
        var uGender : String = "m"
        var uCorp : Boolean = false
        // 날짜 임시 처리
        var uBirth : Date = Date.from(Instant.now())

        // 상단 X 버튼
        binding.returnMain.setOnClickListener {
            finish()
        }

        binding.checkGender.setOnCheckedChangeListener{ _, checked ->
            when(checked){
                R.id.rb_men -> uGender = "m"
                R.id.rb_women -> uGender = "f"
            }
        }

        binding.checkCorp.setOnCheckedChangeListener{ _, checked ->
            when(checked){
                R.id.compNo -> uCorp = false
                R.id.compYes -> uCorp = true
            }
        }

        // 회원가입 버튼 클릭
        binding.signUpBtn.setOnClickListener {
            if(namePass == 0 && emailPass == 0 && pwPass == 0 && pwCheck == 0){
                val uName : String = binding.etUsername.text.toString()
                val uDate : String = binding.tvBirth.text.toString()
                val uNickname : String = binding.etUserNickname.text.toString()
                val uEmail : String = binding.etUsermail.text.toString()
                val uPw : String = binding.etUserpw.text.toString()

                /*var retrofit = Retrofit.Builder()
                    .baseUrl(API.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                var apiService = retrofit.create(ApiService::class.java)*/


                /*apiService.signUp(uEmail, uPw, uName, uNickname, uCorp, uBirth, uGender).enqueue(object :
                    Callback<LoginData> {
                        override fun onFailure(call: Call<LoginData>, t: Throwable) {
                            Log.d("회원가입 실패", "회원가입 실패")
                            Toast.makeText(this@SignupActivity, "서버 오류! 회원가입 실패", Toast.LENGTH_LONG).show()
                        }

                        override fun onResponse(call: Call<LoginData>, response: Response<LoginData>) {
                            TODO("Not yet implemented")
                        }
                    })*/

                var intent = Intent(this, CreateProfile::class.java)
                intent.putExtra("uName",uName)
                intent.putExtra("uDate",uDate)
                intent.putExtra("uNickname",uNickname)
                intent.putExtra("uEmail",uEmail)
                intent.putExtra("uPw",uPw)
                //Toast.makeText(this@SignupActivity, "회원가입이 완료되었습니다.", Toast.LENGTH_LONG).show()
                startActivity(intent)
                //finish()
            }
            else {
                Toast.makeText(this, "빈칸을 모두 제대로 채워주세요!", Toast.LENGTH_LONG).show()
            }
        }

        //날짜 버튼 클릭
        binding.btnBirth.setOnClickListener {
            //calendar Constraint Builder 선택할수있는 날짜 구간설정
            val calendarConstraintBulder = CalendarConstraints.Builder()
            //오늘 이전만 선택가능하게 하는 코드
            calendarConstraintBulder.setValidator(DateValidatorPointBackward.now())

            val builder = MaterialDatePicker.Builder.datePicker()
                .setTitleText("생일을 선택해주세요")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setCalendarConstraints(calendarConstraintBulder.build())
            val datePicker = builder.build()

            datePicker.addOnPositiveButtonClickListener {
                val calendar = Calendar.getInstance()
                calendar.time = Date(it)
                val calendarMilli = calendar.timeInMillis
                binding.tvBirth.setText("${calendar.get(Calendar.MONTH) + 1}/${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(Calendar.YEAR)}")
                // 나이 계산
                Log.d("날짜 테스트", calendar.toString())
            }
            datePicker.show(supportFragmentManager,datePicker.toString())
        }
    }

    // 이름 확인
    private fun nameFocusListener(){
        binding.etUsername.setOnFocusChangeListener { _, focused ->
            if(!focused){
                binding.tlUsername.helperText = validName()
            }
        }
    }
    private fun validName(): String?
    {
        val nameText = binding.etUsername.text.toString()
        if(nameText.length < 2){
            namePass = 1
            return "최소 2자 이상 입력해주세요."
        } else { namePass = 0}

        if(!nameText.matches("^[a-zA-Zㄱ-ㅎ가-힣]*\$".toRegex())){
            namePass = 1
            return "한글 및 영어만 입력해주세요."
        } else { namePass = 0 }

        return null
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

    // 비밀번호 확인 유효성 검증
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