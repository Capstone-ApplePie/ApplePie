package com.example.project_applepie

import org.intellij.lang.annotations.Language
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.PUT

data class ResponseDc(var result:String? = null)

data class Login(
    var code: Int
)

public interface ApiService {
    // 1. 회원가입
    @POST("/users/signup") // 임시 작업
    @FormUrlEncoded
    fun logUp(@Field("name") name : String,
              @Field("email") email : String,
              @Field("password") password : String,
              @Field("nickname") nickname : String,
              @Field("corp") corp : Boolean
    ) : Call<Login>

    // 2. 로그인
    @POST("/users/login")
    @FormUrlEncoded
    fun logIn(@Field("email") email: String,
              @Field("password") password: String
    )

    // 4. 회원 프로필 생성
    @POST("/users/profile/{id}")
    @FormUrlEncoded
    fun createProfile(@Field("area") area : String,
                      @Field("college") college : String,
                      @Field("grade") grade : Float,
                      @Field("grader") grader : String,
                      @Field("github") github : String,
                      @Field("devLanguage") devLanguage: String,
                      @Field("devFramework") devFramework : String
    )

    // 5. 회원 프로필 수정
    @PUT("/users/profile/{id}")
    @FormUrlEncoded
    fun modifyProfile(@Field("area") area : String,
                      @Field("college") college : String,
                      @Field("grade") grade : Float,
                      @Field("grader") grader : String,
                      @Field("github") github : String,
                      @Field("devLanguage") devLanguage: String,
                      @Field("devFramework") devFramework : String
    )

    // 7. 글 작성
//    @POST("/boards")
//    @FormUrlEncoded


}

