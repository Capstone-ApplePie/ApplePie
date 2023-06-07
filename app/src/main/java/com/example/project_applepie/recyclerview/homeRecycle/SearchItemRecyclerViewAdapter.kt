package com.example.project_applepie.recyclerview.homeRecycle

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.project_applepie.App
import com.example.project_applepie.R
import com.example.project_applepie.model.recuit

class SearchItemRecyclerViewAdapter(private val context : Context) : RecyclerView.Adapter<SearchItemViewHolder>(){

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

        holder.apply {
            Glide.with(context)
                .load(searchList[position].thumbnail)
                .into(photoImageView)
        }

        holder.recruitTitle.text = searchList[position].title
        holder.recuitDetail.text = searchList[position].detail

        if(position < 3){
            holder.recuritBackground.setBackgroundColor(Color.parseColor("#FFE4C4"))
        }

        if(position!=RecyclerView.NO_POSITION){
            holder.itemView.setOnClickListener {
                listener?.onItemClick(holder.itemView, searchList[position], position)
            }
        }
    }

    fun submitList(searchList : ArrayList<recuit>){
        this.searchList = searchList
    }

    interface OnItemClickListener{
        fun onItemClick(v:View, data: recuit, pos : Int)
    }
    private var listener : OnItemClickListener? = null
    fun setOnItemClickListener(listener : OnItemClickListener) {
        this.listener = listener
    }
}
