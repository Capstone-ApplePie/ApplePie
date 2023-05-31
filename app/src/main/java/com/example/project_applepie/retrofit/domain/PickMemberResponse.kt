package com.example.project_applepie.retrofit.domain

data class PickMemberResponse(
    var status : Int,
    var code : String,
    var message : String,
    var totalCount : List<Int>,
    var count : List<Int>
)