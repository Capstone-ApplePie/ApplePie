package com.example.project_applepie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.project_applepie.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val homeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(homeBinding.root)

        replaceFragment(RecruitFragment())

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
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.fl_home,fragment).commit()
    }
}