package com.example.project_applepie.recyclerview.profileRecycle

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.project_applepie.App
import com.example.project_applepie.PersonalInformation
import com.example.project_applepie.R
import com.example.project_applepie.model.AuerProfile
import com.example.project_applepie.model.dao.createBoard
import com.example.project_applepie.model.myBoard
import com.example.project_applepie.retrofit.ApiService
import com.example.project_applepie.retrofit.domain.BasicResponse
import com.example.project_applepie.utils.Url
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.coroutineContext

class myBoardAdapter : RecyclerView.Adapter<myBoardHoler>() {

    private var searchList = ArrayList<myBoard>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myBoardHoler {
        val searchMyBoard = myBoardHoler(LayoutInflater
            .from(parent.context)
            .inflate(R.layout.layout_my_board,parent,false))
        return searchMyBoard
    }

    override fun getItemCount(): Int {
        return searchList.size
    }

    override fun onBindViewHolder(holder: myBoardHoler, position: Int) {
        holder.boardImg.load(searchList[position].thumbnail)
        holder.boardName.text = searchList[position].title
        val boardId = searchList[position].id.toString()
        var fragPos = position

        // 서버 연동 코드
        val url = Url.BASE_URL
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var server = retrofit.create(ApiService::class.java)

        holder.boardDelte.setOnClickListener{
            Log.d("로그","삭제, $boardId")
            server.deleteBoard(boardId).enqueue(object : Callback<BasicResponse>{
                override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>
                ) {
                    searchList.removeAt(fragPos)
                    Log.d("성공 로그","$fragPos 번 삭제")
                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                    Log.d("실패 로그","삭제 실패")
                }
            })
        }
        holder.boardModify.setOnClickListener{
            val intent = Intent(holder.itemView?.context, createBoard::class.java)
            intent.putExtra("bid", boardId)
            ContextCompat.startActivity(holder.itemView.context, intent, null)
            Log.d("로그","수정, $boardId")
        }

        if(position!=RecyclerView.NO_POSITION){
            holder.itemView.setOnClickListener {
                listener?.onItemClick(holder.itemView, searchList[position], position)
            }
        }
    }

    fun submitList(searchList : ArrayList<myBoard>){
        this.searchList = searchList
    }
    interface OnItemClickListener{
        fun onItemClick(v: View, data: myBoard, pos : Int)
    }
    private var listener : OnItemClickListener? = null
    fun setOnItemClickListener(listener : OnItemClickListener) {
        this.listener = listener
    }
}