package com.example.project_applepie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import com.bumptech.glide.Glide
import com.example.project_applepie.databinding.ActivityRecruitTeamBinding
import com.example.project_applepie.model.boardDetailBoard
import com.example.project_applepie.model.recuit
import com.example.project_applepie.retrofit.ApiService
import com.example.project_applepie.retrofit.domain.BoardDetailResponse
import com.example.project_applepie.utils.Url
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RecruitTeamActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rtBinding = ActivityRecruitTeamBinding.inflate(layoutInflater)
        setContentView(rtBinding.root)

        val intent = intent
        val recruitData = intent.getSerializableExtra("data") as recuit?
        val bid = recruitData?.id

        val url = Url.BASE_URL
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var server = retrofit.create(ApiService::class.java)

        server.searchBoardDetails(bid!!).enqueue(object : Callback<BoardDetailResponse>{
            override fun onResponse(call: Call<BoardDetailResponse>, response: Response<BoardDetailResponse>
            ) {
                if(response.isSuccessful){
                    val deadline = response.body()?.board?.get("deadline")
                    val dead = deadline.toString().substring(1,deadline.toString().lastIndex-1)
                    Log.d("로그 - 서버성공","deadline : $deadline")
                    rtBinding.tvDeadline.text = "모집마감 : ~ " + dead
                }

            }

            override fun onFailure(call: Call<BoardDetailResponse>, t: Throwable) {
                Log.d("로그 - 에러","${t.localizedMessage}")
            }

        })

        Glide.with(this).load(recruitData?.thumbnail).into(rtBinding.ivRtImg)
        rtBinding.tvRtTitle.text = recruitData?.title
        rtBinding.tvRtDetail.text = recruitData?.detail

        val getRole = resources.getStringArray(R.array.select_role)
        val arrayAdapter = ArrayAdapter(this,R.layout.dropdown_item, getRole)
        rtBinding.actvRole.setAdapter(arrayAdapter)

        //드롭박스 선택 인원수 확인
        rtBinding.actvRole.setOnItemClickListener { adapterView, view, i, l ->
            Log.d("로그","역할 선택 : $i")
        }

    }
}