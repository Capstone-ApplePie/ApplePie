package com.example.project_applepie.model.dao

import com.google.gson.JsonObject
import okhttp3.MultipartBody

data class WriteBoardBoard(
    var nickname: String,
    var id: Int,
    var title: String,
    var content: String,
    var view_count: Int,
    var category: String,
    var deadline: String,
    var files: List<MultipartBody.Part>
)