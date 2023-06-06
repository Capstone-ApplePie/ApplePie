package com.example.project_applepie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project_applepie.databinding.ActivityViewVolunteerAcitviyBinding
import com.example.project_applepie.model.AuerProfile
import com.example.project_applepie.model.dao.searchVolunteer
import com.example.project_applepie.model.myTeam
import com.example.project_applepie.model.recuit
import com.example.project_applepie.model.volunteerList
import com.example.project_applepie.recyclerview.profileRecycle.MyTeamAdapter
import com.example.project_applepie.recyclerview.profileRecycle.myVolunteerAdapter
import com.example.project_applepie.recyclerview.profileRecycle.viewTeamAdapter
import com.example.project_applepie.retrofit.ApiService
import com.example.project_applepie.retrofit.domain.SearchVolunteerResponse
import com.example.project_applepie.sharedpref.SharedPref
import com.example.project_applepie.utils.Url
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ViewVolunteerAcitviy : AppCompatActivity() {
    private lateinit var volunteerAdapter : myVolunteerAdapter
    private lateinit var viewTeamAdapter: viewTeamAdapter
    private lateinit var vvBinding: ActivityViewVolunteerAcitviyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vvBinding = ActivityViewVolunteerAcitviyBinding.inflate(layoutInflater)
        setContentView(vvBinding.root)

        val data = intent.getSerializableExtra("data") as myTeam?
        val teamId = searchVolunteer(data!!.id)
        val uid = SharedPref.getUserId(this)

        val url = Url.BASE_URL
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var server = retrofit.create(ApiService::class.java)

        var totalCount : List<Int> = ArrayList()
        var count : List<Int> = ArrayList()
        val volunteers : ArrayList<volunteerList> = ArrayList()

        server.searchVolunteer(uid,teamId).enqueue(object : Callback<SearchVolunteerResponse>{
            override fun onResponse(call: Call<SearchVolunteerResponse>, response: Response<SearchVolunteerResponse>
            ) {
                if(response.isSuccessful){
                    Log.d("로그 - 지원자리스트조회","${response.body()}")
                    val jsonArr = response.body()?.volunteerList
                    try{
                        totalCount = response.body()?.totalCount!!
                        count = response.body()!!.count
                    }catch (e : RuntimeException){
                        Log.d("에러 - total,count","${e.localizedMessage}")
                    }
                    try{
                        for(i in 0..jsonArr!!.size()-1){
                            val jsonObj = jsonArr.get(i).asJsonObject
                            try{
                                var vol = volunteerList(jsonObj.getAsJsonPrimitive("createAt").asString,
                                    jsonObj.getAsJsonPrimitive("updateAt").asString, jsonObj.getAsJsonPrimitive("status").asInt,
                                    jsonObj.getAsJsonPrimitive("id").asInt,
                                    jsonObj.getAsJsonPrimitive("role").asString,jsonObj.getAsJsonPrimitive("application").asString,
                                    jsonObj.getAsJsonPrimitive("volunteerStatus").asString)
                                volunteers.add(vol)
                            }catch (e : RuntimeException){
                                Log.d("에러 - 지원자리스트_json","${e.localizedMessage}")
                            }
                        }
                    }catch (e : RuntimeException){
                        Log.d("에러 - volunteerlist","${e.localizedMessage}")
                    }

                }
            }

            override fun onFailure(call: Call<SearchVolunteerResponse>, t: Throwable) {
                Log.d("에러 - retrofit","${t.localizedMessage}")
            }

        })

        // 상단 뒤로가기 버튼 활성화
        vvBinding.topAppBar.setNavigationOnClickListener { _ ->
            finish()
        }

        val basicImg = R.drawable.charmander

        val itemList = arrayListOf(
            AuerProfile(basicImg,"이상해씨","백엔드","이상해해햏")
        )

        volunteerAdapter = myVolunteerAdapter()
        volunteerAdapter.submitList(itemList)
        vvBinding.rvVolunteer.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)
        vvBinding.rvVolunteer.adapter = volunteerAdapter

        viewTeamAdapter = viewTeamAdapter()
        viewTeamAdapter.submitList(itemList)
        vvBinding.rvTeamMember.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)
        vvBinding.rvTeamMember.adapter = viewTeamAdapter
    }
}