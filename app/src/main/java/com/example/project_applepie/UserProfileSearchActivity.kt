package com.example.project_applepie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.project_applepie.databinding.ActivityUserProfileSearchBinding
import com.example.project_applepie.model.AuerProfile
import com.example.project_applepie.model.recuit

class UserProfileSearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val upsaBinding = ActivityUserProfileSearchBinding.inflate(layoutInflater)
        setContentView(upsaBinding.root)

        val intent = intent
        val userData = intent.getSerializableExtra("data") as AuerProfile?

        upsaBinding.tvUesrSearchName.text = userData?.uname
        upsaBinding.ivUserSearch.setImageResource(userData?.img!!)
        upsaBinding.tvUserSearchTag.text = userData.tag
        upsaBinding.tvUserSearchIntro.text = userData.udetail
    }
}