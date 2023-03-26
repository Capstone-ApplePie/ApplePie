package com.example.project_applepie.recyclerview

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.project_applepie.App
import com.example.project_applepie.R
import com.example.project_applepie.model.recuit

class SearchItemViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

    private val photoImageView = itemView.findViewById<ImageView>(R.id.recruit_imageView)
    private val recruitTitle = itemView.findViewById<TextView>(R.id.recruit_title)
    private val recuitDetail = itemView.findViewById<TextView>(R.id.recuit_detail)

    fun bindWithView(recuitItem : recuit){
        recruitTitle.text = recuitItem.title
        recuitDetail.text = recuitItem.detail

        Glide.with(App.instance)
            .load(recuitItem.thumbnail)
            .placeholder(R.drawable.baseline_insert_photo_24)
            .into(photoImageView)
    }

}