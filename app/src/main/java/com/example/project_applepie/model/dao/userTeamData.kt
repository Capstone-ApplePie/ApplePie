package com.example.project_applepie.model.dao

data class userTeamData(
    var createdAt : String,
    var updateAt : String,
    var status : String,
    var id : Int,
    var teamName : String,
    var teamContent : String,
    var count : List<Int>,
    var teamStatus : String
)