package com.example.project_applepie.model.dao

import com.example.project_applepie.model.teamRole

data class createTeam(
    var boardId : Int,
    var teamName : String,
    var teamContent : String,
    var count : ArrayList<teamRole>,
    var role : Int
)
