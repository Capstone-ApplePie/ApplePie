package com.example.project_applepie.retrofit.domain

import com.example.project_applepie.model.boardList

data class BoardResponse(
    var status: Int,
    var code: String,
    var message: String,
    var boardList: List<boardList>
)
