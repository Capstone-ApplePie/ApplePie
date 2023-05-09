package com.example.project_applepie.recyclerview.profileRecycle

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.project_applepie.R
import com.example.project_applepie.model.AuerProfile
import com.example.project_applepie.model.myTeam

class myVolunteerAdapter : RecyclerView.Adapter<myVolunteerHolder>(){

    private var searchList = ArrayList<AuerProfile>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myVolunteerHolder {
        val teamHolder = myVolunteerHolder(LayoutInflater
            .from(parent.context)
            .inflate(R.layout.layout_volunteer_item,parent,false)
        )
        return teamHolder
    }

    override fun getItemCount(): Int {
        return searchList.size
    }

    override fun onBindViewHolder(holder: myVolunteerHolder, position: Int) {
        holder.teamImg.setImageResource(searchList.get(position).img)
        holder.teamTitle.text = searchList.get(position).uname
        holder.teamTag.text = searchList.get(position).tag
        holder.teamDetail.text = searchList.get(position).udetail

        holder.teamDelete.setOnClickListener {
            Log.d("로그","삭제")
        }

        if(position!=RecyclerView.NO_POSITION){
            holder.itemView.setOnClickListener {
                listener?.onItemClick(holder.itemView, searchList[position], position)
            }
        }
    }

    fun submitList(searchList : ArrayList<AuerProfile>){
        this.searchList = searchList
    }
    interface OnItemClickListener{
        fun onItemClick(v: View, data: AuerProfile, pos : Int)
    }
    private var listener : OnItemClickListener? = null
    fun setOnItemClickListener(listener : OnItemClickListener) {
        this.listener = listener
    }
}