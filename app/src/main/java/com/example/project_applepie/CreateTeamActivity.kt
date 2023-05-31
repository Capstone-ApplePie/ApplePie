package com.example.project_applepie

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import coil.load
import com.bumptech.glide.Glide
import com.example.project_applepie.databinding.ActivityCreateTeamBinding
import com.example.project_applepie.model.dao.sinup
import com.example.project_applepie.retrofit.ApiService
import com.example.project_applepie.utils.Url
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
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

    // 임시 (확실 치 않음)
    var file = File("")

    // 사진 가져오기 (1)
    private val readImage = registerForActivityResult(ActivityResultContracts.GetContent()){ uri ->
        findViewById<ImageView>(R.id.img_load).load(uri)
        file = File("uri")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ctBinding = ActivityCreateTeamBinding.inflate(layoutInflater)
        setContentView(ctBinding.root)

        val getTeamCount = resources.getStringArray(R.array.create_team_count)
        val arrayAdapter = ArrayAdapter(this,R.layout.dropdown_item, getTeamCount)
        ctBinding.actvBackend.setAdapter(arrayAdapter)
        ctBinding.actvFrontend.setAdapter(arrayAdapter)
        ctBinding.actvDesigner.setAdapter(arrayAdapter)
        ctBinding.actvPm.setAdapter(arrayAdapter)
        ctBinding.actvWebDeveloper.setAdapter(arrayAdapter)

        checkPermission.launch(permissionList)

        // 사진 가져오기 (1)
        ctBinding.btnUpload.setOnClickListener {
            if(readImage != null){
                readImage.launch("image/*")
            }
        }

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

        // 변수 설정
        var uCategory : String = "과제/과외"
        var uDeadline : String = ""
        var uBackend : Int
        var uFrontend : Int
        var uPm : Int
        var uDesigner : Int
        var uWebDev : Int

        //날짜 버튼 클릭
        ctBinding.btnDeadline.setOnClickListener {
            //calendar Constraint Builder 선택할수있는 날짜 구간설정
            val calendarConstraintBulder = CalendarConstraints.Builder()
            //오늘 이후만 선택 가능하도록 하는 코드
            calendarConstraintBulder.setValidator(DateValidatorPointForward.now())

            val builder = MaterialDatePicker.Builder.datePicker()
                .setTitleText("기한일을 선택해주세요")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setCalendarConstraints(calendarConstraintBulder.build())
            val datePicker = builder.build()

            datePicker.addOnPositiveButtonClickListener {
                val calendar = Calendar.getInstance()
                calendar.time = Date(it)

                val calendarMilli = calendar.timeInMillis
                val calendarDateTime = Instant.ofEpochMilli(calendarMilli).atZone(ZoneId.systemDefault()).toLocalDateTime()
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                val formatted = calendarDateTime.format(formatter)

                val sb = StringBuilder()
                sb.append(calendar.get(Calendar.YEAR))
                sb.append("-")
                if(Integer.parseInt((calendar.get(Calendar.MONTH)+1).toString()) < 10){
                    sb.append("0")

                }
                sb.append(calendar.get(Calendar.MONTH)+1)
                sb.append("-")
                if(Integer.parseInt((calendar.get(Calendar.DAY_OF_MONTH)).toString()) < 10){
                    sb.append("0")
                }
                sb.append(calendar.get(Calendar.DAY_OF_MONTH))

                ctBinding.tvBirth.setText(sb.toString())
                uDeadline = formatted
            }
            datePicker.show(supportFragmentManager,datePicker.toString())
        }

        // 사용자 설정 (순서대로) -> 백 / 프론트 / 디자이너 / 기획 / 웹개발
        ctBinding.actvBackend.setOnItemClickListener{ adapterView, view, position, id ->
            uBackend = position
//            Log.d("숫자 확인", "$uBackend")
        }

        ctBinding.actvFrontend.setOnItemClickListener { adapterView, view, position, id ->  }


        ctBinding.toggleButton.check(R.id.button2)
        ctBinding.button1.setOnClickListener {
            uCategory = "외주" // TODO: 회사가 아니면 선택 불가능하도록
        }
        ctBinding.button2.setOnClickListener {
            uCategory = "과제/과외"
        }
        ctBinding.button3.setOnClickListener {
            uCategory = "공모전"
        }

        // 글 작성 버튼 클릭
        ctBinding.creating.setOnClickListener {
            // Retrofit 연동
            val url = Url.BASE_URL
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            var server = retrofit.create(ApiService::class.java)

            val uTitle : String = ctBinding.title.text.toString()

        }
    }
}

// 사진 전송을 위한 절대경로 변환 https://onedaycodeing.tistory.com/168
fun absolutePath(path: Uri?, context: Context): String {
    var proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
    var c: Cursor? = context.contentResolver.query(path!!, proj, null, null, null)
    var index = c?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
    c?.moveToFirst()

    var result = c?.getString(index!!)

    return result!!
}