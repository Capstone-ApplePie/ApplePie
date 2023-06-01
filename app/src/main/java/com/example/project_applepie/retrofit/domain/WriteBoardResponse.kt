package com.example.project_applepie.retrofit.domain

import okhttp3.MultipartBody
import okhttp3.ResponseBody

data class WriteBoardResponse(
    var image: MultipartBody.Part,
    var board: ResponseBody
)
