package com.example.project_applepie.retrofit.domain

import com.example.project_applepie.model.boardDetailBoard

data class BoardDetailResponse(
    var status : String,
    var code : String,
    var message :String,
    var board : boardDetailBoard,
    var team : String
)
