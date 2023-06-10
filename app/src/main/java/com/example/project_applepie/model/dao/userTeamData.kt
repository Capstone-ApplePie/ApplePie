package com.example.project_applepie.model.dao

import com.google.gson.JsonElement

data class userTeamData(
    var createdAt: String,
    var updateAt: String,
    var status: Int,
    var id: Int,
    var teamName: String,
    var teamContent: String,
    var totalCount: JsonElement,
    var count: JsonElement,
    var teamStatus: String
)