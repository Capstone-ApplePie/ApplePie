package com.example.project_applepie.retrofit.domain

import com.example.project_applepie.model.dao.WriteBoardBoard
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.ResponseBody

data class WriteBoardResponse(
    var statud : Int,
    var code : String,
    var message : String,
    var board : JsonObject
)
