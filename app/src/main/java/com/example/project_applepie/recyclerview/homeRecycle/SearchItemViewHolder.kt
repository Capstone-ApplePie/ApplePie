package com.example.project_applepie.recyclerview.homeRecycle

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.project_applepie.App
import com.example.project_applepie.R
import com.example.project_applepie.model.recuit

class SearchItemViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

    val photoImageView = itemView.findViewById<ImageView>(R.id.recruit_imageView)
    val recruitTitle = itemView.findViewById<TextView>(R.id.recruit_title)
    val recuitDetail = itemView.findViewById<TextView>(R.id.recuit_detail)
    val recuritBackground = itemView.findViewById<ConstraintLayout>(R.id.recruit_card)


}