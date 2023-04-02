package com.example.project_applepie.recyclerview

import android.view.LayoutInflater
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
        holder.userImg.setImageResource(searchList.get(position).img)
        holder.userName.text = searchList.get(position).uname
        holder.userTag.text = searchList.get(position).tag
        holder.userDetail.text = searchList.get(position).udetail
    }

    fun submitList(searchList : ArrayList<AuerProfile>){
        this.searchList = searchList
    }

}