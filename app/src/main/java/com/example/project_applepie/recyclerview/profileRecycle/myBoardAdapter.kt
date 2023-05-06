package com.example.project_applepie.recyclerview.profileRecycle

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.project_applepie.App
import com.example.project_applepie.PersonalInformation
import com.example.project_applepie.R
import com.example.project_applepie.model.AuerProfile
import com.example.project_applepie.model.myBoard

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
        holder.boardImg.setImageResource(searchList.get(position).thumbnail)
        holder.boardName.text = searchList.get(position).title

        holder.boardDelte.setOnClickListener{
            Log.d("로그","삭제")
        }
        holder.boardModify.setOnClickListener{
            Log.d("로그","수정")
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