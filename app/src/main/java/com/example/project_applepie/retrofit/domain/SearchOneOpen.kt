package com.example.project_applepie.retrofit.domain

import com.google.gson.JsonArray
import com.google.gson.JsonObject

data class SearchOneOpen(
    var status : Int,
    var code : String,
    var message : String,
    var profiles : JsonObject,
    var info : JsonObject
)
