package com.example.project_applepie.recyclerview.profileRecycle

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project_applepie.R

class myVolunteerHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    val teamImg = itemView.findViewById<ImageView>(R.id.lv_img)
    val teamTitle = itemView.findViewById<TextView>(R.id.lv_name)
    val teamDelete = itemView.findViewById<ImageButton>(R.id.ibtn_delete_volunteer)
    val teamDetail = itemView.findViewById<TextView>(R.id.lv_detail)
    val teamTag = itemView.findViewById<TextView>(R.id.lv_tag)
}