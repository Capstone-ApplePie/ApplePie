package com.example.project_applepie.model.dao

import okhttp3.MultipartBody
import retrofit2.http.Part

data class js_createBoard(
    var image : MultipartBody.Part,
    var id: String,
    var title: String,
    var content: String,
    var category: Int,
    var deadline: String
//    var Backend: Int,
//    var Frontend: Int,
//    var designer: Int,
//    var pm: Int,
//    var webDev: Int,
)
