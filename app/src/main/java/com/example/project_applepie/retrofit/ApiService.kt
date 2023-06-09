package com.example.project_applepie.retrofit

import com.example.project_applepie.model.dao.*
import com.example.project_applepie.retrofit.domain.*
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
        ): Call<BasicResponse>

    // 2. 로그인 (수정 완)
    @POST(API.LOGIN)
    fun logIn(@Body signInModel : js_signIn?
        ): Call<LoginData>

    // 3. 회원 정보 조회
    @GET(API.SEARCH_PROFILE)
    fun inquireUserInfo(
        @Path("uid") uid : String
    ): Call<inquireUserInfo>

    // 5. 회원 프로필 수정 (수정 완)
    @PUT(API.MODIFY_PROFILE)
    fun modifyProfile(
        @Body modProfile : js_modProfile?,
        @Path("uid")uid : String
    ): Call<BasicResponse>

    // 6. 회원 탈퇴
    @DELETE(API.DELETE_ACCOUNT)
    fun deleteUser(@Path("uid") uid : String):Call<BasicResponse>

    // 7. 개인 세부 프로필 조회
    @GET(API.SEARCH_DETAIL_PROFILE)
    fun searchProfileDetails(@Path("pid") pid : String):Call<personalDetailProfile>

    // 28. 개인 프로필 전부 조회
    @GET(API.GET_ALL_DATA)
    fun searchAllFile(@Path("uid") uid : String):Call<personalAll>

    // 8. 개인 세부 프로필 생성
    @POST(API.CREATE_DETAIL_PROFILE)
    fun createDetailProfile(
        @Path("pid") pid : String,
        @Body uInput : js_CDP?
    ) : Call<personalDetailProfile>

    // 9. 개인 세부 프로필 수정
    @PUT(API.MODIFY_DETAIL_PROFILE)
    fun modifyDetailProfile(
        @Path("pid") pid : String,
        @Body uInput : js_CDP?
    ) : Call<personalDetailProfile>

    // 10. open 여부 수정
    @PUT(API.MODIFY_OPEN_PROFILE)
    fun modiOpenProfile(@Path("pid") pid : String,
    @Body modiOpen: modiOpen) : Call<BasicResponse>

    // 11 . 전체 open 프로필 조회
    @POST(API.SEARCH_ALL_OPEN_PROFILE)
    fun searchOpenProfile(@Body searchAllProfiles: SearchAllProfiles) : Call<SearchVolDetailResponse>

    // ~~~. Open 프로필 1개 상세 조회
    @POST(API.SEARCH_ONE_OPEN_PROFILE)
    fun searchOneOpenProfile(@Body searchOneOpen: searchOneOpenPf) : Call<SearchOneOpen>

    // 12. 전체 글 조회 <= 서버 수정 필요
    @POST(API.SEARCH_BOARD)
    fun searchBoard(@Body searchBoard : board?
    ) : Call<BoardResponse>

    // 13. 글 작성 https://ducksever.tistory.com/28
    @Multipart
    @POST(API.WRITE_BOARD)
    fun writeBoard(
        @Part ("board") board : createBoard,
        @Part image: MultipartBody.Part?
    ): Call<WriteBoardResponse>

    // 14. 단일 글 조회(참조 : https://zerodice0.tistory.com/198)
    @GET(API.SEARCH_BOARD_DETAIL)
    @Streaming
    fun searchBoardDetails(@Path("bid") bid : Int) :Call<BoardDetailResponse>

    // 15. 글 삭제
//    @DELETE(API.DELETE_BOARD)
    @Headers("Content-Type: application/json")
    @HTTP(method = "DELETE", path = "/boards/{bid}", hasBody = true)
    fun deleteBoard(@Path("bid") bid : String, @Body userId: userIden) : Call<BasicResponse>
//    fun deleteBoard(@Path("bid") bid : String, @Query("userId") userId: String) : Call<BasicResponse>


    // 16. 글 수정 <= 글 작성 작성 후 수정 필요
    @Multipart
    @PUT(API.MODIFY_BOARD)
    fun modifyBoard(
        @Path("bid") bid : String,
        @Part ("board") board : createBoard,
        @Part image : MultipartBody.Part?
    ) : Call<WriteBoardResponse>

    // 16 - 2 . 유저의 팀 검색
    @GET(API.SEARCH_USER_TEAM)
    fun searchUserTeam(@Path("uid") uid : String) : Call<BoardResponse>

    // 17. 팀 생성
    @POST(API.CREATE_TEAM)
    fun createTeam(
        @Path("uid") uid : String,
        @Body createTeam: CreateTeam) : Call<BasicResponse>

    // 18. 팀 수정
    @PUT(API.CREATE_TEAM)
    fun modifyTeam(@Body createTeam: CreateTeam) : Call<BasicResponse>

    // 20. 팀원으로 지원
    @POST(API.APPLY_VOLUNTEER)
    fun applyVolunteer(@Body applyVolunteer: applyVolunteer) : Call<BasicResponse>

    // 21. 팀원 지원 취소
    @DELETE(API.CANCEL_VOLUNTEER)// 원래 delete는 response없고, body가 아닌 key-value로 전달
    fun cancelVolunteer(@Body cancelVolunteer: cancelVolunteer) : Call<BasicResponse>

    // 22. 유저 팀 모두 조회 및 유저 생성 글 조회
    @GET(API.SEARCH_USER_ALL_DATA)
    fun userAllData(@Path("uid") uid : String) : Call<SearchUserAllDataResponse>

    //24. 팀 취소
    @Headers("Content-Type: application/json")
    @HTTP(method = "DELETE", path = "/teams", hasBody = true)
    fun cancelTeam(@Body cancelTeam: cancelTeam) : Call<BasicResponse>

    // 23. 지원 리스트 조회
    @POST(API.SEARCH_VOLUNTEER)
    fun searchVolunteer(
        @Path("uid") uid: String,
        @Body searchVolunteer : searchVolunteer
    ) : Call<SearchVolunteerResponse>

    // 25. 팀원 선택
    @POST(API.PICK_MEMBER)
    fun pickMember(@Body pickMember: pickMember) : Call<BasicResponse>

    // 26. 팀 삭제
    @DELETE(API.DELETE_TEAM)
    fun delteTeam(
        @Body deleteTeam: deleteTeam
    ) : Call<BasicResponse>

    // 27. 팀원 조회
    @POST(API.SEARCH_VOLUNTEER_MEMBER)
    fun searchVolunteerMember(
        @Body searchVolunteer: searchVolunteer
    ):Call<searchTeamMember>
}

