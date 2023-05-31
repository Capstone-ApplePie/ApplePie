package com.example.project_applepie.model.dao

data class createTeam(
    var boardId : Int,
    var teamName : String,
    var teamContent : String,
    var count : List<Int>,
    var role : Int
)
