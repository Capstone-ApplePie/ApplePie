package com.example.project_applepie.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.project_applepie.R
import com.example.project_applepie.model.recuit
import java.io.FilterReader
import java.util.logging.Filter

class SearchItemRecyclerViewAdapter : RecyclerView.Adapter<SearchItemViewHolder>(){

    private var searchList = ArrayList<recuit>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemViewHolder {
        val searchItemViewHolder = SearchItemViewHolder(LayoutInflater
            .from(parent.context)
            .inflate(R.layout.layout_recruit_item,parent,false))
        return searchItemViewHolder
    }

    override fun getItemCount(): Int {
        return searchList.size
    }

    override fun onBindViewHolder(holder: SearchItemViewHolder, position: Int) {
        holder.photoImageView.setImageResource(searchList.get(position).thumbnail)
        holder.recruitTitle.text = searchList.get(position).title
        holder.recuitDetail.text = searchList.get(position).detail

    }

    fun submitList(searchList : ArrayList<recuit>){
        this.searchList = searchList
    }
}