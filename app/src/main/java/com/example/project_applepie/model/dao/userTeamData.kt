package com.example.project_applepie.model.dao

data class userTeamData(
    var createdAt : String,
    var updateAt : String,
    var status : Int,
    var id : Int,
    var teamName : String,
    var teamContent : String,
    var totalCount : List<String>,
    var count : List<String>,
    var teamStatus : String
)