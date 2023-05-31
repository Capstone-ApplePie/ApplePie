package com.example.project_applepie.retrofit.domain

data class ProfileData(
    var email : String,
    var name : String,
    var nickname : String,
    var corp : String,
    var gender : String,
    var birth : String,
    var area : String,
    var college : String,
    var grade : String,
    var totalGrade : String,
    var github : String,
    var devFramework : String,
    var devLanguage : List<String>
)
