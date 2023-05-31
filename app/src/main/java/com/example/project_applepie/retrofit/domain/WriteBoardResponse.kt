package com.example.project_applepie.retrofit.domain

import okhttp3.MultipartBody

data class WriteBoardResponse(
    var image: MultipartBody.Part,
    var uid: Int,
    var title: String,
    var content: String,
    var category: String,
    var deadline: String
)
