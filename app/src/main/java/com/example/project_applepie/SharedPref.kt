package com.example.project_applepie

import android.content.Context
import android.content.SharedPreferences
import retrofit2.Callback

object SharedPref {
    private val MY_ACCOUNT : String = "account"

    // 임시 작업
    fun setUserId(context: Context, input: String){
        val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = prefs.edit()
        editor.putString("MY_ID", input)
        editor.commit()
    }

    fun getUserId(context: Context): String {
        val prefs: SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        return prefs.getString("MY_ID", "").toString()
    }
}