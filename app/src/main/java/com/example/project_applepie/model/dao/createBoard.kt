package com.example.project_applepie.model.dao

data class createBoard(
    var userId : String,
    var title : String,
    var content : String,
     var category : Int,
    var deadline : String
)