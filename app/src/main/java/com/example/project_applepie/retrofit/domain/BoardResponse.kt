package com.example.project_applepie.retrofit.domain

import com.google.gson.JsonArray
import org.json.JSONArray

data class BoardResponse(
    var status: Int,
    var code: String,
    var message: String,
    var boardList:JsonArray
)
