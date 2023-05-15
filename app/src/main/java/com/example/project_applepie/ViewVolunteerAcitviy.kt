package com.example.project_applepie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project_applepie.databinding.ActivityViewVolunteerAcitviyBinding
import com.example.project_applepie.model.AuerProfile
import com.example.project_applepie.model.myTeam
import com.example.project_applepie.model.recuit
import com.example.project_applepie.recyclerview.profileRecycle.MyTeamAdapter
import com.example.project_applepie.recyclerview.profileRecycle.myVolunteerAdapter
import com.example.project_applepie.recyclerview.profileRecycle.viewTeamAdapter

class ViewVolunteerAcitviy : AppCompatActivity() {
    private lateinit var volunteerAdapter : myVolunteerAdapter
    private lateinit var viewTeamAdapter: viewTeamAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val vvBinding = ActivityViewVolunteerAcitviyBinding.inflate(layoutInflater)
        setContentView(vvBinding.root)

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