package com.example.project_applepie.retrofit.domain

import com.example.project_applepie.model.boardDetailBoard
import com.google.gson.JsonArray
import com.google.gson.JsonObject

data class BoardDetailResponse(
    var status : String,
    var code : String,
    var message :String,
    var board : JsonObject,
    var team : JsonObject
)
