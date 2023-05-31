package com.example.project_applepie.retrofit

import com.example.project_applepie.model.dao.js_createBoard
import com.example.project_applepie.retrofit.domain.LoginData
import com.example.project_applepie.model.dao.js_modProfile
import com.example.project_applepie.model.dao.js_signIn
import com.example.project_applepie.model.dao.sinup
import com.example.project_applepie.retrofit.domain.SignUpResponse
import com.example.project_applepie.retrofit.domain.WriteBoardResponse
import com.example.project_applepie.utils.API
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*
import java.util.*

//data class ResponseDc(var result:String? = null)


interface ApiService {
    // 1. 회원가입 (수정 완)
    @POST(API.SIGN_UP)
    fun signUp(@Body signUpModel : sinup?
        ): Call<SignUpResponse>

    // 2. 로그인 (수정 완)
    @POST(API.LOGIN)
    fun logIn(@Body signInModel : js_signIn?
        ): Call<LoginData>

    // 3. 회원 정보 조회
    @GET(API.SEARCH_PROFILE)
    @FormUrlEncoded
    fun inquireUserInfo(
    ): Call<LoginData>

    // 5. 회원 프로필 수정 (수정 완)
    @PUT(API.MODIFY_PROFILE)
    fun modifyProfile(@Body modProfile : js_modProfile?
    ): Call<LoginData>

    // 6. 회원 탈퇴
    @GET(API.DELETE_ACCOUNT)
    fun deleteUser()

    // 12. 전체 글 조회
    @GET(API.SEARCH_BOARD)
    fun searchBoard()


    // 13. 글 작성 https://velog.io/@tkdwns0301/Kotlin-%EC%9D%B4%EB%AF%B8%EC%A7%80-%EA%B0%80%EC%A0%B8%EC%99%80%EC%84%9C-%EC%84%9C%EB%B2%84%EC%97%90-%EC%A0%84%EB%8B%AC-%ED%9B%84-View%EC%97%90-%EB%9D%84%EC%9A%B0%EA%B8%B0
    @Multipart
    @POST(API.WRITE_BOARD)
    fun writeBoard(
        @Part file: MultipartBody.Part,
        @Part("uid") uid: String,
        @Part("title") title: String,
        @Part("content") content: String,
        @Part("category") category: Int,
        @Part("deadline") deadline: String
    ): Call<WriteBoardResponse>
}

