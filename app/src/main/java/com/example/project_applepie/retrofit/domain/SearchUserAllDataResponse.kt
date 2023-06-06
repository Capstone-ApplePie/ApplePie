package com.example.project_applepie.retrofit.domain

import com.google.gson.JsonArray
import com.google.gson.JsonObject

data class SearchUserAllDataResponse(
    var status : Int,
    var code : String,
    var message : String,
    var boards : JsonArray,
    var complete : JsonArray,
    var incomplete : JsonArray,
    var apply : JsonArray,
    var lesson : Boolean,
    var outsourcing : Boolean,
    var project : Boolean
)