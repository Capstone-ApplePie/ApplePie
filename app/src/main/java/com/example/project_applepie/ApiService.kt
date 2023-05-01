package com.example.project_applepie

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.PUT
import java.util.*

data class ResponseDc(var result:String? = null)

public interface ApiService {
    // 1. 회원가입
    @POST("/users/signup") // 임시 작업
    @FormUrlEncoded
    fun signUp(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("name") name: String,
        @Field("nickname") nickname: String,
        @Field("corp") corp: Boolean,
        @Field("birth") birth: Date,
        @Field("gender") gender: String
        ): Call<LoginData>
}

public interface LoginService {
    // 2. 로그인
    @POST("/users/login")
    @FormUrlEncoded
    fun logIn(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginData>
}

public interface ProfileService {
    // 4. 회원 프로필 생성
    @POST("/users/profile/{id}")
    @FormUrlEncoded
    fun createProfile(
        @Field("area") area: String,
        @Field("college") college: String,
        @Field("grade") grade: Float,
        @Field("grader") grader: String,
        @Field("github") github: String,
        @Field("devLanguage") devLanguage: String,
        @Field("devFramework") devFramework: String
    )
}

public interface ProfileModify {
    // 5. 회원 프로필 수정
    @PUT("/users/profile/{id}")
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
}

public interface Writing{
    // 7. 글 작성
//    @POST("/boards")
//    @FormUrlEncoded


}

