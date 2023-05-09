package com.example.project_applepie.recyclerview.profileRecycle

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project_applepie.R

class viewTeamHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    val teamImg = itemView.findViewById<ImageView>(R.id.lpi_img)
    val teamTitle = itemView.findViewById<TextView>(R.id.lpi_name)
    val teamDetail = itemView.findViewById<TextView>(R.id.lpi_detail)
    val teamTag = itemView.findViewById<TextView>(R.id.lpi_tag)
}