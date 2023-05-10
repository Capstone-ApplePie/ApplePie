package com.example.project_applepie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.project_applepie.databinding.ActivityModifyProfileBinding

class ModifyProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mpBinding = ActivityModifyProfileBinding.inflate(layoutInflater)
        setContentView(mpBinding.root)

        val getLanguage = resources.getStringArray(R.array.create_profile_language)
        val arrayAdapter = ArrayAdapter(this,R.layout.dropdown_item, getLanguage)
        mpBinding.acLanguage.setAdapter(arrayAdapter)

        val getFramework = resources.getStringArray(R.array.create_profile_framework)
        val arrayAdapter2 = ArrayAdapter(this,R.layout.dropdown_item, getFramework)
        mpBinding.acFramework.setAdapter(arrayAdapter2)
    }
}