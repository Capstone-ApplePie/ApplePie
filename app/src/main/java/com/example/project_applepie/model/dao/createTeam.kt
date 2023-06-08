package com.example.project_applepie.model.dao

import com.example.project_applepie.model.teamRole

data class CreateTeam(
    var boardId : Int,
    var teamName : String,
    var teamContent : String,
    var count : ArrayList<Int>,
    var role : Int
)
