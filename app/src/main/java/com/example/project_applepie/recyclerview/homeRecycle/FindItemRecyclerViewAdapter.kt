package com.example.project_applepie.recyclerview.homeRecycle

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.project_applepie.R
import com.example.project_applepie.model.chating

class FindItemRecyclerViewAdapter : RecyclerView.Adapter<FindItemViewHolder>(){

    private var findList = ArrayList<chating>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FindItemViewHolder {
        val findItemViewHolder = FindItemViewHolder(LayoutInflater
            .from(parent.context)
            .inflate(R.layout.layout_chat_item,parent,false))
        return findItemViewHolder
    }

    override fun getItemCount(): Int {
        return findList.size
    }

    override fun onBindViewHolder(holder: FindItemViewHolder, position: Int) {
        holder.userImageView.setImageResource(findList.get(position).image)
        holder.chatSender.text = findList.get(position).sender
        holder.chatContent.text = findList.get(position).content

    }

    fun submitFindList(searchList : ArrayList<chating>){
        this.findList = searchList
    }
}