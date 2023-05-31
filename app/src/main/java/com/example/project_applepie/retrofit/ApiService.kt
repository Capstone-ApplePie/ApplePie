package com.example.project_applepie.retrofit

import com.example.project_applepie.retrofit.domain.LoginData
import com.example.project_applepie.model.dao.js_modProfile
import com.example.project_applepie.model.dao.js_signIn
import com.example.project_applepie.model.dao.sinup
import com.example.project_applepie.retrofit.domain.SignUpResponse
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
    //fun searchBoard(@)


    // 13. 글 작성 https://ducksever.tistory.com/28
    @Multipart
    @POST(API.WRITE_BOARD)
    fun writeBoard(
        @Part imageList : List<MultipartBody.Part?>
    ): Call<LoginData>
}

