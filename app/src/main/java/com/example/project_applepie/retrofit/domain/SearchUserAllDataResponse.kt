package com.example.project_applepie.retrofit.domain

import com.google.gson.JsonObject

data class SearchUserAllDataResponse(
    var status : Int,
    var code : String,
    var message : String,
    var board : List<JsonObject>,
    var complete : List<JsonObject>,
    var incomplete : List<JsonObject>,
    var apply : List<JsonObject>,
    var lesson : Boolean,
    var outsourcing : Boolean,
    var project : Boolean
)