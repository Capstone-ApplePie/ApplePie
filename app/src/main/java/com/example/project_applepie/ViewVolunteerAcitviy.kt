package com.example.project_applepie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project_applepie.databinding.ActivityViewVolunteerAcitviyBinding
import com.example.project_applepie.model.AuerProfile
import com.example.project_applepie.model.dao.searchOneOpenPf
import com.example.project_applepie.model.myTeam
import com.example.project_applepie.model.recuit
import com.example.project_applepie.recyclerview.profileRecycle.MyTeamAdapter
import com.example.project_applepie.recyclerview.profileRecycle.myVolunteerAdapter
import com.example.project_applepie.recyclerview.profileRecycle.viewTeamAdapter
import com.example.project_applepie.retrofit.ApiService
import com.example.project_applepie.utils.Url
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

        // 상단 뒤로가기 버튼 활성화
        vvBinding.topAppBar.setNavigationOnClickListener { _ ->
            finish()
        }

        val intent = intent
        val aUserProData = intent.getSerializableExtra("data") as AuerProfile?
        val oid = aUserProData!!.oid
        val cate = aUserProData!!.tag

//        if(cate == "OUTSOURCING")

//        val url = Url.BASE_URL
//        val retrofit = Retrofit.Builder()
//            .baseUrl(url)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        var server = retrofit.create(ApiService::class.java)
//
//        val searchOOModel = searchOneOpenPf(cate, oid)
//
//        server.searchOneOpenProfile()

        val basicImg = R.drawable.charmander

        val itemList = arrayListOf(
            AuerProfile(basicImg,"이상해씨","백엔드","이상해해햏", oid)
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