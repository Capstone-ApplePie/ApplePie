package com.example.project_applepie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.project_applepie.databinding.ActivityModifyDetailProfileBinding
import com.example.project_applepie.databinding.ActivityUserProfileSearchBinding
import com.example.project_applepie.model.AuerProfile
import com.example.project_applepie.model.recuit

class UserProfileSearchActivity : AppCompatActivity() {

    private lateinit var upsaBinding: ActivityUserProfileSearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        upsaBinding = ActivityUserProfileSearchBinding.inflate(layoutInflater)
        setContentView(upsaBinding.root)

        // 상단 뒤로가기 버튼 활성화
        upsaBinding.topAppBar.setNavigationOnClickListener { _ ->
            finish()
        }

        val intent = intent
        val userData = intent.getSerializableExtra("data") as AuerProfile?

        upsaBinding.tvUesrSearchName.text = userData?.uname
        upsaBinding.ivUserSearch.setImageResource(userData?.img!!)
        upsaBinding.tvUserSearchTag.text = userData.tag
        upsaBinding.tvUserSearchIntro.text = userData.udetail
    }
}