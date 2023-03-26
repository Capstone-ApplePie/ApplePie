package com.example.project_applepie.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.project_applepie.R
import com.example.project_applepie.model.recuit

class SearchItemRecyclerViewAdapter : RecyclerView.Adapter<SearchItemViewHolder>() {

    private var searchList = ArrayList<recuit>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemViewHolder {
        val searchItemViewHolder = SearchItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_recruit_item,parent,false))
        return searchItemViewHolder
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: SearchItemViewHolder, position: Int) {
        holder.bindWithView(this.searchList[position])
    }

    fun submitList(seachList : ArrayList<recuit>){
        this.searchList = searchList
    }
}