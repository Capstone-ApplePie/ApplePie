package com.example.project_applepie

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.example.project_applepie.databinding.ActivityCreateTeamBinding
import com.example.project_applepie.retrofit.ApiService
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.*

class CreateTeamActivity : AppCompatActivity() {

    private lateinit var ctBinding : ActivityCreateTeamBinding

    // 갤러리에서 사진 가져오기
    private val permissionList = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
    private val checkPermission = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){ result ->
        result.forEach{
            if(!it.value){
                Toast.makeText(applicationContext, "권한 동의 필요!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    // Retrofit 연동
//        val url = "여기에 서버 주소 입력"
//        val retrofit = Retrofit.Builder()
//            .baseUrl(url)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        var server = retrofit.create(ApiService::class.java)

    // 사진 가져오기 (1)
//    private val readImage = registerForActivityResult(ActivityResultContracts.GetContent()){ uri ->
//        ctBinding.imgLoad.load(uri)
//    }

    // 사진 가져오기 (2)
    private val activityResult: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){

        // 결과 코드 OK, 결과값 null 아니면
        if(it.resultCode == RESULT_OK && it.data != null){
            // 값 담기
            val uri = it.data!!.data

            // 화면에 보여주기
            Glide.with(this)
                .load(uri) // 이미지
                .into(ctBinding.imgLoad)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ctBinding = ActivityCreateTeamBinding.inflate(layoutInflater)
        setContentView(ctBinding.root)

        val getTeamCount = resources.getStringArray(R.array.create_team_count)
        val arrayAdapter = ArrayAdapter(this,R.layout.dropdown_item, getTeamCount)

        ctBinding.actvBackend.setAdapter(arrayAdapter)
        ctBinding.actvFrontend.setAdapter(arrayAdapter)

        checkPermission.launch(permissionList)



        // 사진 가져오기 (1)
//         ctBinding.btnUpload.setOnClickListener {
//             readImage.launch("image/*")
//         }

        // 사진 가져오기 (2)
        ctBinding.btnUpload.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            activityResult.launch(intent)
        }


        // 카테고리 기본값 : 외주
        var uCategory : String = "Outsourcing"
        ctBinding.toggleButton.check(R.id.button1)
        ctBinding.button1.setOnClickListener {
            uCategory = "Outsourcing"
        }
        ctBinding.button2.setOnClickListener {
            uCategory = "lesson"
        }
        ctBinding.button3.setOnClickListener {
            uCategory = "project"
        }

//        ctBinding.backEnd.


        // 날짜 버튼 클릭
        ctBinding.btnDeadline.setOnClickListener {
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
                ctBinding.tvBirth.setText("${calendar.get(Calendar.MONTH) + 1}/${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(
                    Calendar.YEAR)}")
                // 나이 계산
                Log.d("날짜 테스트", calendar.toString())
            }
            datePicker.show(supportFragmentManager,datePicker.toString())
        }

        ctBinding.creating.setOnClickListener {
            // Retrofit 연동
//            val url = "여기에 서버 주소 입력"
//            val retrofit = Retrofit.Builder()
//                .baseUrl(url)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//
//            var server = retrofit.create(ApiService::class.java)

//            val uTitle : String = ctBinding.title.text.toString()
//            val uText : String = ctBinding.writeText.text.toString()

            val intent = Intent(this@CreateTeamActivity, HomeActivity::class.java)
            Toast.makeText(this, "글 작성이 완료되었습니다.", Toast.LENGTH_LONG).show()
            startActivity(intent)
            finish()
        }
    }
}