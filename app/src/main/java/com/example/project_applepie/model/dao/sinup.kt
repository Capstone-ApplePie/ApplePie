package com.example.project_applepie.model.dao

data class sinup(
    var email: String?,
    var password: String?,
    var name: String?,
    var nickname: String?,
    var corp: Boolean,
    var birth: String?,
    var gender: String?,
    var area: String,
    var college: String,
    var grade: Float,
    var totalGrade: Float,
    var grader: String,
    var github: String,
    var devLanguage: List<Int>,
    var devFramework: String
)
