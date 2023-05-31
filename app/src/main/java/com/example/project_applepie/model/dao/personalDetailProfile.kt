package com.example.project_applepie.model.dao

import com.example.project_applepie.model.historyWork

data class personalDetailProfile (
    var status : Int,
    var code : String,
    var message : String,
    var lesson : List<historyWork>,
    var project : List<historyWork>,
    var outsourcing : List<historyWork>
)