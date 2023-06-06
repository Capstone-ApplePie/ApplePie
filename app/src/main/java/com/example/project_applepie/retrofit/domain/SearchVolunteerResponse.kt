package com.example.project_applepie.retrofit.domain

import com.example.project_applepie.model.volunteerList
import com.google.gson.JsonArray

data class SearchVolunteerResponse(
    var status : Int,
    var code : String,
    var message : String,
    var totalCount : List<Int>,
    var count : List<Int>,
    var volunteerList : JsonArray
)
