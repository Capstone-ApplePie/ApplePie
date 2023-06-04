package com.example.project_applepie.model.dao

import com.google.gson.JsonObject

data class personalDetailProfile (
    var status : Int,
    var code : String,
    var message : String,
    var lesson : JsonObject,
    var project : JsonObject,
    var outsourcing : JsonObject
)