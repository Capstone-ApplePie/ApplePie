package com.example.project_applepie.model

import okhttp3.MultipartBody

data class boardList(
    var id : Int?,
    var title : String?,
    var content : String?,
    var view_count : Int?,
    var categoryId : String?,
    var file : String,
    var deadline : String?,
    var status : Boolean
)
