package com.example.project_applepie.model

import okhttp3.MultipartBody

data class boardList(
    var id : String?,
    var title : String?,
    var content : String?,
    var view_count : String?,
    var categoryId : String?,
    var file : MultipartBody.Part,
    var deadline : String?,
    var status : Boolean
)
