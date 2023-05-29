package com.example.project_applepie.model.dao

import retrofit2.http.Field

data class js_modProfile(
    var area: String,
    var collegue: String,
    var grade: Float,
    var totalGrade: Float,
    var grader: String,
    var github: String,
    var devLanguage: List<Int>,
    var devFramework: String
)
