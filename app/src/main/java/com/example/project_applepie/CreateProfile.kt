package com.example.project_applepie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.project_applepie.databinding.ActivityCreateProfileBinding

class CreateProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val cpBinding = ActivityCreateProfileBinding.inflate(layoutInflater)
        setContentView(cpBinding.root)

        val getLanguage = resources.getStringArray(R.array.create_profile_language)
        val arrayAdapter = ArrayAdapter(this,R.layout.dropdown_item, getLanguage)
        cpBinding.acLanguage.setAdapter(arrayAdapter)

        val getFramework = resources.getStringArray(R.array.create_profile_language)
        val arrayAdapter2 = ArrayAdapter(this,R.layout.dropdown_item, getFramework)
        cpBinding.acLanguage.setAdapter(arrayAdapter)
    }
}