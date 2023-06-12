package com.example.project_applepie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project_applepie.databinding.ActivityViewVolunteerAcitviyBinding
import com.example.project_applepie.model.*
import com.example.project_applepie.model.dao.pickMember
import com.example.project_applepie.model.dao.searchTeamMember
import com.example.project_applepie.model.dao.searchVolunteer
import com.example.project_applepie.recyclerview.homeRecycle.SearchTeamRecyclerViewAdapter
import com.example.project_applepie.recyclerview.profileRecycle.myVolunteerAdapter
import com.example.project_applepie.recyclerview.profileRecycle.viewTeamAdapter
import com.example.project_applepie.retrofit.ApiService
import com.example.project_applepie.retrofit.domain.BasicResponse
import com.example.project_applepie.retrofit.domain.SearchVolunteerResponse
import com.example.project_applepie.sharedpref.SharedPref
import com.example.project_applepie.utils.Url
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ViewVolunteerAcitviy : AppCompatActivity() {
    private lateinit var volunteerAdapter : SearchTeamRecyclerViewAdapter
    private lateinit var viewTeamAdapter: SearchTeamRecyclerViewAdapter
//    private lateinit var volunteerAdapter : myVolunteerAdapter
//    private lateinit var viewTeamAdapter: viewTeamAdapter
    private lateinit var vvBinding: ActivityViewVolunteerAcitviyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vvBinding = ActivityViewVolunteerAcitviyBinding.inflate(layoutInflater)
        setContentView(vvBinding.root)

        val data = intent.getSerializableExtra("data") as myTeam?
        val teamId = searchVolunteer(data?.id)
        val uid = SharedPref.getUserId(this)

        // 임시 데이터
//        val itemList2 = arrayListOf(
//            AuerProfile2("https://firebasestorage.googleapis.com/v0/b/applepie-f030c.appspot.com/o/file36-1?alt=media",
//                "이상해씨","백엔드","이상해해",6)
//        )

        Log.d("로그 - uid","$uid")
        Log.d("로그 - teamId","$teamId")

        val url = Url.BASE_URL
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var server = retrofit.create(ApiService::class.java)

        var totalCount : List<Int> = ArrayList()
        var count : List<Int> = ArrayList()
        val volunteers : ArrayList<VolunteerList> = ArrayList()
        var volMember : ArrayList<AuerProfile> = ArrayList()

        server.searchVolunteer(uid,teamId).enqueue(object : Callback<SearchVolunteerResponse>{
            override fun onResponse(call: Call<SearchVolunteerResponse>, response: Response<SearchVolunteerResponse>
            ) {
                if(response.isSuccessful){
                    Log.d("로그 - 지원자 리스트 조회","${response.body()}")
                    val jsonArr = response.body()?.volunteerList
                    try{
                        totalCount = response.body()?.totalCount!!
                        count = response.body()!!.count
                    }catch (e : RuntimeException){
                        Log.d("에러 - total,count","${e.localizedMessage}")
                    }
                    try{
                        for(i in 0 until jsonArr!!.size()){
                            val jsonObj = jsonArr.get(i).asJsonObject
                            try{
                                var vol = VolunteerList(jsonObj.getAsJsonPrimitive("volunteerId").asString,
                                    jsonObj.getAsJsonPrimitive("role").asString, jsonObj.getAsJsonPrimitive("volunteerStatus").asString)

//                                val emptyImage = "https://firebasestorage.googleapis.com/v0/b/applepie-f030c.appspot.com/o/file36-1?alt=media"
                                val emptyImage = R.drawable.user
//                                val memName = jsonObj.getAsJsonPrimitive("nickname").asString
                                val volName = "-"
                                val volRole = jsonObj.getAsJsonPrimitive("role").asString
                                val emptyDetail = "지원한 사람"
                                val volId = jsonObj.getAsJsonPrimitive("volunteerId").asString
                                val volMem = AuerProfile(emptyImage, volName, volRole, emptyDetail, volId)
                                volunteers.add(vol)
                                volMember.add(volMem)
                            }catch (e : RuntimeException){
                                Log.d("에러 - 지원자 리스트_json","${e.localizedMessage}")
                            }
                        }
                        Log.d("지원자 확인", "$volunteers + $volMember")
                    }catch (e : RuntimeException){
                        Log.d("에러 - volunteerList","${e.localizedMessage}")
                    }

                    var checkMem = 0
                    // 지원한 사람
                    volunteerAdapter = SearchTeamRecyclerViewAdapter()
                    if(volMember.isEmpty()){
                        Log.d("지원한 사람 땜빵 성공","$volMember")
//                        val cryingImg = (R.drawable.crying).toString()
//                        val emptyName = "None"
//                        val emptyRole = "None"
//                        val emptyContent = "지원한 사람 없음"
//                        val emptyId = 0
//                        val emptyVol = AuerProfile2(cryingImg, emptyName, emptyRole, emptyContent, emptyId)
                        val cryingImg = R.drawable.crying
                        val emptyName = "None"
                        val emptyRole = "None"
                        val emptyContent = "지원한 사람 없음"
                        val emptyId = "0"
                        val emptyVol = AuerProfile(cryingImg, emptyName, emptyRole, emptyContent, emptyId)
                        checkMem = 1
                        volMember.add(emptyVol)
                    }

                    volunteerAdapter.submitList(volMember)
                    vvBinding.rvVolunteer.layoutManager = LinearLayoutManager(this@ViewVolunteerAcitviy,
                        LinearLayoutManager.VERTICAL,false)
                    vvBinding.rvVolunteer.adapter = volunteerAdapter

//                    volunteerAdapter.setOnItemClickListener(object : SearchTeamRecyclerViewAdapter.OnItemClickListener{
//                        override fun onItemClick(v: View, data: AuerProfile, pos: Int) {
//                            val intent = Intent(this@ViewVolunteerAcitviy, UserProfileSearchActivity::class.java)
//                            intent.putExtra("data",data)
//                            startActivity(intent)
//                        }
//                    })

                    if(checkMem == 0){
                        volunteerAdapter.setOnItemClickListener(object : SearchTeamRecyclerViewAdapter.OnItemClickListener{
                            override fun onItemClick(v: View, data: AuerProfile, pos: Int) {
                                val volId = pickMember(data.oid)
                                server.pickMember(volId).enqueue(object : Callback<BasicResponse>{
                                    override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>
                                    ) {
                                        if(response.isSuccessful){
                                            Log.d("로그 - 팀 선택","${response.body().toString()}")
                                        }
                                    }
                                    override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                                        Log.d("에러 - 서버","${t.localizedMessage}")
                                    }
                                })
                            }
                        })
                    }
                }
            }
            override fun onFailure(call: Call<SearchVolunteerResponse>, t: Throwable) {
                Log.d("에러 - retrofit","${t.localizedMessage}")
            }

        })

//        var teamMembers : ArrayList<AuerProfile2> = ArrayList()
        var teamMembers : ArrayList<AuerProfile> = ArrayList()

        server.searchVolunteerMember(teamId).enqueue(object : Callback<searchTeamMember>{
            override fun onResponse(call: Call<searchTeamMember>, response: Response<searchTeamMember>
            ) {
                if(response.isSuccessful){
                    Log.d("로그 - 내 팀원 리스트 조회","${response.body()}")
                    val jsonArr = response.body()?.member
                    try{
                        for(i in 0 until jsonArr!!.size()){
                            val jsonObj = jsonArr.get(i).asJsonObject
                            try{
//                                val emptyImage = "https://firebasestorage.googleapis.com/v0/b/applepie-f030c.appspot.com/o/file36-1?alt=media"
//                                val memName = jsonObj.getAsJsonPrimitive("nickname").asString
//                                val memRole = jsonObj.getAsJsonPrimitive("role").asString
//                                val memDetail = "내 팀원"
//                                val memId = 0
//                                val mem = AuerProfile2(emptyImage, memName, memRole, memDetail, memId)
                                val emptyImage = R.drawable.user
                                val memName = jsonObj.getAsJsonPrimitive("nickname").asString
                                val memRole = jsonObj.getAsJsonPrimitive("role").asString
                                val memDetail = "내 팀원"
                                val memId = "0"
                                val mem = AuerProfile(emptyImage, memName, memRole, memDetail, memId)
                                teamMembers.add(mem)
                                Log.d("volunteerMember check", "$teamMembers")
                            }catch (e : RuntimeException){
                                Log.d("에러 - 팀원 리스트_json","${e.localizedMessage}")
                            }
                        }
                    }catch (e : RuntimeException){
                        Log.d("에러 - volunteerList","${e.localizedMessage}")
                    }

                    viewTeamAdapter = SearchTeamRecyclerViewAdapter()
                    viewTeamAdapter.submitList(teamMembers)
                    vvBinding.rvTeamMember.layoutManager = LinearLayoutManager(this@ViewVolunteerAcitviy,
                        LinearLayoutManager.VERTICAL,false)
                    vvBinding.rvTeamMember.adapter = viewTeamAdapter

                    viewTeamAdapter.setOnItemClickListener(object : SearchTeamRecyclerViewAdapter.OnItemClickListener{
                        override fun onItemClick(v: View, data: AuerProfile, pos: Int) {
                            val intent = Intent(this@ViewVolunteerAcitviy, UserProfileSearchActivity::class.java)
                            intent.putExtra("data",data)
                            startActivity(intent)
                        }
                    })

                }
            }

            override fun onFailure(call: Call<searchTeamMember>, t: Throwable) {
                Log.d("에러 - retrofit","${t.localizedMessage}")
            }

        })

        // 상단 뒤로가기 버튼 활성화
        vvBinding.topAppBar.setNavigationOnClickListener { _ ->
            finish()
        }

        val itemList = arrayListOf(
            AuerProfile2("https://firebasestorage.googleapis.com/v0/b/applepie-f030c.appspot.com/o/file36-1?alt=media",
                "이상해씨","백엔드","이상해해",6)
        )

        // 지원한 사람
//        volunteerAdapter = myVolunteerAdapter(this)
//        volunteerAdapter.submitList(itemList)
//        vvBinding.rvVolunteer.layoutManager = LinearLayoutManager(this,
//            LinearLayoutManager.VERTICAL,false)
//        vvBinding.rvVolunteer.adapter = volunteerAdapter

//        if(volMember.isNotEmpty()){
//            volunteerAdapter.setOnItemClickListener(object : SearchTeamRecyclerViewAdapter.OnItemClickListener{
//                override fun onItemClick(v: View, data: AuerProfile, pos: Int) {
//                    val volId = pickMember(data.oid)
//                    server.pickMember(volId).enqueue(object : Callback<BasicResponse>{
//                        override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>
//                        ) {
//                            if(response.isSuccessful){
//                                Log.d("로그 - 팀 선택","${response.body().toString()}")
//                            }
//                        }
//                        override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
//                            Log.d("에러 - 서버","${t.localizedMessage}")
//                        }
//                    })
//                }
//            })
//        }

        // 내 팀원
        viewTeamAdapter = SearchTeamRecyclerViewAdapter()
        viewTeamAdapter.submitList(teamMembers)
        vvBinding.rvTeamMember.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)
        vvBinding.rvTeamMember.adapter = viewTeamAdapter
    }
}