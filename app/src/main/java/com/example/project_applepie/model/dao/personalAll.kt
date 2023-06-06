package com.example.project_applepie.model.dao

import com.google.gson.JsonArray
import com.google.gson.JsonObject

data class personalAll(
    var status : Int,
    var code : String,
    var message : String,
    var profileId : String,
    var data : JsonObject,
    var lesson : JsonObject,
    var project : JsonObject,
    var outsourcing : JsonObject
)
