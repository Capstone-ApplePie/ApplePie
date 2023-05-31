package com.example.project_applepie

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import coil.load
import com.example.project_applepie.databinding.ActivityCreateBoardBinding
import com.example.project_applepie.model.dao.js_createBoard
import com.example.project_applepie.retrofit.ApiService
import com.example.project_applepie.retrofit.domain.WriteBoardResponse
import com.example.project_applepie.sharedpref.SharedPref
import com.example.project_applepie.utils.Url
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class CreateBoardActivity : AppCompatActivity() {

    private lateinit var ctBinding : ActivityCreateBoardBinding

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
//        Log.d("uri 확인", uri.toString())
        file = File(absolutePath(uri, this))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ctBinding = ActivityCreateBoardBinding.inflate(layoutInflater)
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
//        ctBinding.btnDeadline.setOnClickListener {
//            //calendar Constraint Builder 선택할수있는 날짜 구간설정
//            val calendarConstraintBulder = CalendarConstraints.Builder()
//            //오늘 이전만 선택가능하게 하는 코드
//            calendarConstraintBulder.setValidator(DateValidatorPointBackward.now())
//
//            val builder = MaterialDatePicker.Builder.datePicker()
//                .setTitleText("생일을 선택해주세요")
//                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
//                .setCalendarConstraints(calendarConstraintBulder.build())
//            val datePicker = builder.build()
//
//            datePicker.addOnPositiveButtonClickListener {
//                val calendar = Calendar.getInstance()
//                calendar.time = Date(it)
//                val calendarMilli = calendar.timeInMillis
//                ctBinding.tvBirth.setText("${calendar.get(Calendar.MONTH) + 1}/${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(
//                    Calendar.YEAR)}")
//                // 나이 계산
//                Log.d("날짜 테스트", calendar.toString())
//            }
//            datePicker.show(supportFragmentManager,datePicker.toString())
//        }

        // 변수 설정
        val uid = SharedPref.getUserId(this@CreateBoardActivity)
        var uCategory : Int = 1
        var uDeadline : String = ""
        var uBackend : Int = 0
        var uFrontend : Int = 0
        var uPm : Int = 0
        var uDesigner : Int = 0
        var uWebDev : Int = 0
        var uContent : String = "" // TODO: 글자 제한 같은 세부사항 생각 필요


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
            uBackend = position }
        ctBinding.actvFrontend.setOnItemClickListener { adapterView, view, position, id ->
            uFrontend = position }
        ctBinding.actvDesigner.setOnItemClickListener { adapterView, view, position, id ->
            uDesigner = position }
        ctBinding.actvPm.setOnItemClickListener { adapterView, view, position, id ->
            uPm = position }
        ctBinding.actvWebDeveloper.setOnItemClickListener { adapterView, view, position, id ->
            uWebDev = position }


        ctBinding.toggleButton.check(R.id.button2)
        ctBinding.button1.setOnClickListener {
            uCategory = 0 // TODO: 회사가 아니면 선택 불가능하도록
        }
        ctBinding.button2.setOnClickListener {
            uCategory = 1
        }
        ctBinding.button3.setOnClickListener {
            uCategory = 2
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
            uContent = ctBinding.writeText.text.toString()
            val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
            val body: MultipartBody.Part = MultipartBody.Part.createFormData("photo", file.name, requestFile)
//            val createBoardModel = js_createBoard(body, uid, uTitle, uContent, uCategory, uDeadline)

            server.writeBoard(body, uid, uTitle, uContent, uCategory, uDeadline).enqueue(object : Callback<WriteBoardResponse>{
                override fun onFailure(call: Call<WriteBoardResponse>, t: Throwable) {
//                    Log.d("글 작성 실패", "글 작성 실패")
                    Log.d("글 작성 실패", "$t")
                }

                override fun onResponse(
                    call: Call<WriteBoardResponse>,
                    response: Response<WriteBoardResponse>
                ) {
                    Toast.makeText(this@CreateBoardActivity, "글 생성이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@CreateBoardActivity, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            })
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