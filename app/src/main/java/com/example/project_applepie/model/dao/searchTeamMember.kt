package com.example.project_applepie.model.dao

import com.example.project_applepie.model.member
import com.google.gson.JsonObject

data class searchTeamMember(
    var status : Int,
    var code : String,
    var message : String,
    var member : List<JsonObject>
)