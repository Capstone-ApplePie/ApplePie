package com.example.project_applepie.sharedpref

import android.content.Context
import android.content.SharedPreferences

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

    fun setPid(context: Context, input: String){
        val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = prefs.edit()
        editor.putString("MY_PID", input)
        editor.commit()
    }

    fun getPid(context: Context): String {
        val prefs: SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        return prefs.getString("MY_PID", "").toString()
    }

    // 최초 1회 실행을 위한 SharedPreference
    fun setFirstTime(context: Context, key: String, value: Boolean){
        val prefs = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putBoolean(key, value)
        editor.commit()
    }

    fun getFirstTime(context: Context, key: String) : Boolean {
        val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        return prefs.getBoolean(key, false)
    }
}