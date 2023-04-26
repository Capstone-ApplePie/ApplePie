package com.example.project_applepie

import java.util.*

data class LoginData(
    var status: Int,
    var code: String,
    var message: String,
    var name: String,
    var email: String,
    var date: Date,
    var corp: Boolean,
    var gender : String
)
