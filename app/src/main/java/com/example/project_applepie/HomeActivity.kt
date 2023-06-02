package com.example.project_applepie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.project_applepie.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val homeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(homeBinding.root)

        val chgV = intent.getIntExtra("chgV", 1)

        if(chgV == 1){
            replaceFragment(RecruitFragment())
        } else if (chgV == 100){
            replaceFragment(PersonalInformation())
        }


        homeBinding.bottomNavigation.setOnItemSelectedListener{item ->
            when(item.itemId){
                R.id.item_recruit -> {
                    replaceFragment(RecruitFragment())
                    true
                }
                R.id.item_team -> {
                    replaceFragment(TeamFragment())
                    true
                }
                R.id.item_chat -> {
                    replaceFragment(ChatFragment())
                    true
                }
                R.id.item_profile -> {
                    replaceFragment(PersonalInformation())
                    true
                }
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.fl_home,fragment).commit()
    }



    // 뒤로 가기 방지를 위한 변수
    private var doubleBackToExit = false

    // 뒤로가기 방지 및 두번 뒤로가기 시 종료
    override fun onBackPressed() {
        if(doubleBackToExit){
            finishAffinity()
        } else {
            Toast.makeText(this, "종료하시려면 뒤로가기를 한번 더 눌러주세요", Toast.LENGTH_SHORT).show()
            doubleBackToExit = true
            runDelayed(1500L){
                doubleBackToExit = false
            }
        }
    }

    // 뒤로 가기를 일정 시간 내에 두번 입력하는 것을 감지하는 함수
    private fun runDelayed(millis: Long, function: () -> Unit){
        Handler(Looper.getMainLooper()).postDelayed(function, millis)
    }
}