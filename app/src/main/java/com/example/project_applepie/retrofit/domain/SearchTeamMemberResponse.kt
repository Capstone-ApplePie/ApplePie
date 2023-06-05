package com.example.project_applepie.retrofit.domain

import com.google.gson.JsonObject

data class SearchTeamMemberResponse(
    var status : Int,
    var code : String,
    var message : String,
    var member : JsonObject
)
