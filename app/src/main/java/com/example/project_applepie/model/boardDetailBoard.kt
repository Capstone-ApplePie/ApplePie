package com.example.project_applepie.model

import okhttp3.MultipartBody

data class boardDetailBoard(
    var nickname : String,
    var id : Int,
    var title : String,
    var content : String,
    var view_count : Int,
    var category : String,
    var deadline : String,
    var files : List<MultipartBody.Part>,
    var status : Boolean
)
