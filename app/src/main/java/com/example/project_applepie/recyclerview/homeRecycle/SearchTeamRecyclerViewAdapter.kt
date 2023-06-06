package com.example.project_applepie.recyclerview.homeRecycle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.project_applepie.R
import com.example.project_applepie.model.AuerProfile

class SearchTeamRecyclerViewAdapter : RecyclerView.Adapter<SearchTeamViewHolder>(){

    private var searchList = ArrayList<AuerProfile>();

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchTeamViewHolder {
        val searchTeamViewHolder = SearchTeamViewHolder(LayoutInflater
            .from(parent.context)
            .inflate(R.layout.layout_profile_item,parent,false))
        return searchTeamViewHolder
    }

    override fun getItemCount(): Int {
        return searchList.size
    }

    override fun onBindViewHolder(holder: SearchTeamViewHolder, position: Int) {
        holder.userImg.setImageResource(searchList[position].img)
        holder.userName.text = searchList[position].uname
        holder.userTag.text = searchList[position].tag
        holder.userDetail.text = searchList[position].udetail

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