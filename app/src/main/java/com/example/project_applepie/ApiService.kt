package com.example.project_applepie

import android.telecom.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

data class ResponseDc(var result:String? = null)

public interface ApiService {
    @POST("/users/signUp") // 임시 작업
    @FormUrlEncoded
    fun logUp(@Field("name") name : String,
              @Field("id") id : String,
              @Field("pw") pw : String
    ) : Call
}