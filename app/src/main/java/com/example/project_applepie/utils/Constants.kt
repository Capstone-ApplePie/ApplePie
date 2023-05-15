package com.example.project_applepie.utils

import retrofit2.http.DELETE

object API{
    const val BASE_URL : String = ""//여기에 url 삽입
    const val SIGN_UP : String = "/users/signup" // 회원가입
    const val LOGIN : String = "/users/login" // 로그인
    const val SEARCH_PROFILE : String = "/users/profile/{id}" // 회원 정보 조회
    const val MODIFY_PROFILE : String = "/users/profile/{id}" // 회원 정보 수정
    const val DELETE_ACCOUNT : String = "/users/{id}" // 회원 탈퇴
    const val SEARCH_DETAIL_PROFILE : String = "/users/profiles/{pid}" // 개인 세부 프로필조회
    const val CREATE_DETAIL_PROFILE : String = "/profiles/{pid}" // 세부 프로필 생성
    const val MODIFY_DETAIL_PROFILE : String = "/profiles/{pid}" // 세부 프로필 수정
    const val MODIFY_OPEN_PROFILE : String = "/profiles/open/{pid}" // open 여부 수정
    const val SEARCH_ALL_OPEN_PROFILE : String = "/profiles" // 전체 open 프로필 조회
    const val SEARCH_BOARD : String = "/boards" // 전체 글 조회
    const val WRITE_BOARD : String = "/boards" // 글 작성
    const val SEARCH_BOARD_DETAIL : String = "/boards/{id}" // 단일 글 조회 (팀 정보 포함)
    const val DELETE_BOARD : String = "/boards/{id}" // 글 삭제
    const val MODIFY_BOARD : String = "/boards/{id}" // 글 수정
    const val CREATE_TEAM : String = "/teams"
    const val APPLY_VOLUNTEER : String = "/teams/volunteer"
    const val CANCEL_VOLUNTEER : String = "/teams/volunteer"
    const val CANCEL_TEAM : String = "/teams"
}