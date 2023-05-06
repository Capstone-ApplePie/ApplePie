package com.example.project_applepie.recyclerview.homeRecycle

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project_applepie.R

class SearchTeamViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
    val userImg = itemView.findViewById<ImageView>(R.id.lpi_img);
    val userName = itemView.findViewById<TextView>(R.id.lpi_name);
    val userTag = itemView.findViewById<TextView>(R.id.lpi_tag);
    val userDetail = itemView.findViewById<TextView>(R.id.lpi_detail);

    //각각의 요소들을 가져와서 viewholder를 통해서 묶어주는 것
}