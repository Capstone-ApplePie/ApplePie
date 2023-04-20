package com.example.project_applepie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.project_applepie.databinding.ActivityCreateTeamBinding

class CreateTeamActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ctBinding = ActivityCreateTeamBinding.inflate(layoutInflater)
        setContentView(ctBinding.root)

        val getTeamCount = resources.getStringArray(R.array.create_team_count)
        val arrayAdapter = ArrayAdapter(this,R.layout.dropdown_item, getTeamCount)
        ctBinding.autoCompleteTextView.setAdapter(arrayAdapter)
    }
}