package com.example.project_applepie.model.dao

import com.example.project_applepie.model.member

data class searchTeamMember(
    var status : Int,
    var code : String,
    var message : String,
    var member : List<member>
)