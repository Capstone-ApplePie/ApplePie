package com.example.project_applepie.retrofit

import com.example.project_applepie.LoginData
import com.example.project_applepie.utils.API
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.PUT
import java.util.*

//data class ResponseDc(var result:String? = null)


interface ApiService {
    // 1. 회원가입
    @POST(API.SIGN_UP) // 임시 작업
    @FormUrlEncoded
    fun signUp(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("name") name: String,
        @Field("nickname") nickname: String,
        @Field("corp") corp: Boolean,
        @Field("birth") birth: Date,
        @Field("gender") gender: String,
        @Field("area") area: String,
        @Field("college") college: String,
        @Field("grade") grade: Float,
        @Field("grader") grader: String,
        @Field("github") github: String,
        @Field("devLanguage") devLanguage: String,
        @Field("devFramework") devFramework: String
        ): Call<LoginData>

    // 2. 로그인
    @POST(API.LOGIN)
    @FormUrlEncoded
    fun logIn(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginData>

    // 5. 회원 프로필 수정
    @PUT(API.MODIFY_PROFILE)
    @FormUrlEncoded
    fun modifyProfile(
        @Field("area") area: String,
        @Field("college") college: String,
        @Field("grade") grade: Float,
        @Field("grader") grader: String,
        @Field("github") github: String,
        @Field("devLanguage") devLanguage: String,
        @Field("devFramework") devFramework: String
    )

    // 7. 글 작성
//    @POST("/boards")
//    @FormUrlEncoded
}

