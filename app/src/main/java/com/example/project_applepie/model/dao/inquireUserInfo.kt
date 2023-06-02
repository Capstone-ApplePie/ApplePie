package com.example.project_applepie.model.dao

import com.google.gson.JsonObject

data class inquireUserInfo(
    var status : Int,
    var code : String,
    var message : String,
    var profileId : Int,
    var data : JsonObject
)
