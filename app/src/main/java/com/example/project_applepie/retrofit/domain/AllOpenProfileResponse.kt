package com.example.project_applepie.retrofit.domain

import com.example.project_applepie.model.openProfileData

data class AllOpenProfileResponse(
    var status : Int,
    var code : String,
    var message : String,
    var data : List<openProfileData>
)