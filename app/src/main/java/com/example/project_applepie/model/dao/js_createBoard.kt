package com.example.project_applepie.model.dao

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Part

data class js_createBoard(
    var image : MultipartBody.Part,
    var boardData : RequestBody
)
