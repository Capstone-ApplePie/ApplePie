package com.example.project_applepie.retrofit.domain

data class LoginData(
    var status: Int,
    var code: String,
    var message: String,
    var uid: String,
    var pid: String,
    var corp: Boolean
)
