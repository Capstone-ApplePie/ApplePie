package com.example.project_applepie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project_applepie.databinding.ActivityViewTeamBinding
import com.example.project_applepie.model.AuerProfile
import com.example.project_applepie.model.myTeam
import com.example.project_applepie.model.recuit
import com.example.project_applepie.recyclerview.profileRecycle.myVolunteerAdapter
import com.example.project_applepie.recyclerview.profileRecycle.viewTeamAdapter
import com.example.project_applepie.retrofit.ApiService
import com.example.project_applepie.retrofit.domain.SearchUserAllDataResponse
import com.example.project_applepie.sharedpref.SharedPref
import com.example.project_applepie.utils.Url
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ViewTeamActivity : AppCompatActivity() {
    private lateinit var viewTeamAdapter: viewTeamAdapter
    private lateinit var vtBinding : ActivityViewTeamBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vtBinding = ActivityViewTeamBinding.inflate(layoutInflater)
        setContentView(vtBinding.root)

        // 상단 뒤로가기 버튼 활성화
        vtBinding.topAppBar.setNavigationOnClickListener { _ ->
            finish()
        }

        val url = Url.BASE_URL
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var server = retrofit.create(ApiService::class.java)

        var uid = SharedPref.getUserId(this)


        val basicImg = R.drawable.charmander
        val itemList = arrayListOf(
            AuerProfile(basicImg,"이상해씨","프론트","간략하게 작성", "1")
        )

        viewTeamAdapter = viewTeamAdapter()
        viewTeamAdapter.submitList(itemList)
        vtBinding.rvRecruit.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)
        vtBinding.rvRecruit.adapter = viewTeamAdapter
    }
}