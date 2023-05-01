package com.example.project_applepie.utils

import retrofit2.http.DELETE

object API{
    const val BASE_URL : String = ""//여기에 url 삽입
    const val SIGN_UP : String = "/users/signup"
    const val LOGIN : String = "/users/login"
    const val SEARCH_PROFILE : String = "/users/profile/{id}"
    const val MODIFY_PROFILE : String = "/users/profile/{id}"
    const val DELETE_ACCOUNT : String = "/users/{id}"
    const val SEARCH_DETAIL_PROFILE : String = "/users/profiles/{pid}"
    const val WRITE_BOARD : String = "/boards"
    const val SEARCH_BOARD_DETAIL : String = "/boards/{id}"
    const val SEARCH_BOARD : String = "/boards"
    const val DELETE_BOARD : String = "/boards/{id}"
    const val MODIFY_BOARD : String = "/boards/{id}"
    const val CREATE_TEAM : String = "/teams"
    const val APPLY_VOLUNTEER : String = "/teams/volunteer"
    const val CANCEL_VOLUNTEER : String = "/teams/volunteer"
    const val CANCEL_TEAM : String = "/teams"
}