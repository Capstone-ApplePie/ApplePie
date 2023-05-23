package com.example.project_applepie.retrofit

import android.util.Log
import com.example.project_applepie.LoginData
import com.example.project_applepie.model.dao.sinup
import com.example.project_applepie.utils.API
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*
import java.util.*

//data class ResponseDc(var result:String? = null)


interface ApiService {
    // 1. 회원가입
    @POST(API.SIGN_UP) // 임시 작업
    fun signUp(@Body signUpModel : sinup?
        ): Call<LoginData>

    // 2. 로그인
    @POST(API.LOGIN)
    @FormUrlEncoded
    fun logIn(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginData>

    // 3. 회원 정보 조회
    @GET(API.SEARCH_PROFILE)
    @FormUrlEncoded
    fun inquireUserInfo(
    ): Call<LoginData>

    // 5. 회원 프로필 수정
    @PUT(API.MODIFY_PROFILE)
    @FormUrlEncoded
    fun modifyProfile(
        @Field("area") area: String,
        @Field("college") college: String,
        @Field("grade") grade: Float,
        @Field("totalGrade") totalGrade: Float,
        @Field("grader") grader: String,
        @Field("github") github: String,
        @Field("devLanguage") devLanguage: Int,
        @Field("devFramework") devFramework: String
    ): Call<LoginData>

    // 6. 회원 탈퇴
    @GET(API.DELETE_ACCOUNT)
    fun deleteUser()


    // 13. 글 작성 https://ducksever.tistory.com/28
    @Multipart
    @POST(API.WRITE_BOARD)
    fun writeBoard(
        @Part imageList : List<MultipartBody.Part?>
    ): Call<LoginData>
}

