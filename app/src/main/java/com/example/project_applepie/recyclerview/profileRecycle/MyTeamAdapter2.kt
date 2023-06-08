package com.example.project_applepie.recyclerview.profileRecycle

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.project_applepie.R
import com.example.project_applepie.model.dao.cancelVolunteer
import com.example.project_applepie.model.myTeam
import com.example.project_applepie.retrofit.ApiService
import com.example.project_applepie.retrofit.domain.BasicResponse
import com.example.project_applepie.sharedpref.SharedPref
import com.example.project_applepie.utils.Url
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MyTeamAdapter2(private val context : Context) : RecyclerView.Adapter<MyTeamHolder>(){

    private var searchList = ArrayList<myTeam>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyTeamHolder {
        val teamHolder = MyTeamHolder(LayoutInflater
            .from(parent.context)
            .inflate(R.layout.layout_create_my_team,parent,false)
        )
        return teamHolder
    }

    override fun getItemCount(): Int {
        return searchList.size
    }

    override fun onBindViewHolder(holder: MyTeamHolder, position: Int) {
        holder.teamImg.load(searchList[position].thumbnail)
        holder.teamTitle.text = searchList[position].title

        val uid = SharedPref.getUserId(context)

        val url = Url.BASE_URL
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var server = retrofit.create(ApiService::class.java)
        val req = cancelVolunteer(searchList[position].id,Integer.parseInt(uid))

        holder.teamDelete.setOnClickListener {
            server.cancelVolunteer(req).enqueue(object : Callback<BasicResponse>{
                override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                    if(response.isSuccessful){
                        Log.d("로그 - 팀지원 취소","${response.body().toString()}")
                    }
                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                    Log.d("로그 - 서버에러","${t.localizedMessage}")
                }

            })
        }

        if(position!=RecyclerView.NO_POSITION){
            holder.itemView.setOnClickListener {
                listener?.onItemClick(holder.itemView, searchList[position], position)
            }
        }
    }

    fun submitList(searchList : ArrayList<myTeam>){
        this.searchList = searchList
    }
    interface OnItemClickListener{
        fun onItemClick(v: View, data: myTeam, pos : Int)
    }
    private var listener : OnItemClickListener? = null
    fun setOnItemClickListener(listener : OnItemClickListener) {
        this.listener = listener
    }
}